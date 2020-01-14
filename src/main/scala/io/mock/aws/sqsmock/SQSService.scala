package io.mock.aws.sqsmock

import akka.actor.ActorSystem
import akka.event.Logging
import akka.event.slf4j.Logger
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions._
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by shutty on 3/29/16.
  */
class SQSService(port:Int, account:Int = 1) {
  val config = ConfigFactory.parseMap(Map("akka.http.parsing.illegal-header-warnings" -> "off"))
  implicit val system = ActorSystem.create("sqsmock", config)
  def start():Unit = {
    val log = Logger(system.getClass, "sqs_client")
    implicit val mat = ActorMaterializer()
    val http = Http(system)
    val backend = new SQSBackend(account, port, system)
    val route =
      logRequest("request", Logging.DebugLevel) {
        pathPrefix(IntNumber) { accountId =>
          path(Segment) { queueName =>
            post {
              formFieldMap { fields =>
                complete {
                  backend.process(fields + ("QueueUrl" -> s"http://localhost:$port/$account/$queueName"))
                }
              }
            }
          }
        } ~ post {
          formFieldMap { fields =>
            complete {
              backend.process(fields + ("Prefix" -> s"http://localhost:$port/$account/"))
            }
          }
        }
      }
    Await.result(http.bindAndHandle(route, "localhost", port), Duration.Inf)
  }

  def shutdown():Unit = Await.result(system.terminate(), Duration.Inf)
  def block():Unit = Await.result(system.whenTerminated, Duration.Inf)
}

object SQSService {
  def main(args: Array[String]) {
    val sqs = new SQSService(8001, 1)
    sqs.start()
    sqs.block()
  }
}

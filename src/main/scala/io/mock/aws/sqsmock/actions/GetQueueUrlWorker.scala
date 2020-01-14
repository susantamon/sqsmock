package io.mock.aws.sqsmock.actions

import akka.actor.ActorSystem
import akka.event.slf4j.Logger
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import io.mock.aws.sqsmock.messages.{ErrorResponse, GetQueueUrlResponse}
import io.mock.aws.sqsmock.model.{Message, QueueCache}

import scala.collection.mutable

/**
  * Created by mondal on 1/14/2020
  */
class GetQueueUrlWorker(account: Long, queues:mutable.Map[String, QueueCache], system: ActorSystem) extends Worker {
  val log = Logger(this.getClass, "get_queue_url_worker")
  def process(fields: Map[String,String]) = {
    val result = for (
      queueName <- fields.get("QueueName");
      queueUrl <- fields.get("Prefix")
    ) yield {
      log.debug(s"queue url $queueUrl$queueName")
      val message = Message(queueUrl + queueName)
      HttpResponse(StatusCodes.OK, entity = GetQueueUrlResponse(message).toXML.toString())
    }
    result.getOrElse {
      log.warn("cannot send message: possibly, some request parameter is missing")
      HttpResponse(StatusCodes.BadRequest, entity = ErrorResponse("Sender", "InvalidParameterValue", "oops").toXML.toString())
    }
  }
}

package io.mock.aws.sqsmock

import com.amazonaws.auth.{AWSStaticCredentialsProvider, AnonymousAWSCredentials}
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.{AmazonSQS, AmazonSQSClientBuilder}
import org.scalatest.{BeforeAndAfterAll, FlatSpec}

/**
  * Created by shutty on 3/31/16.
  */
trait SQSStartStop extends FlatSpec with BeforeAndAfterAll {
  var sqs:SQSService = _
  var client:AmazonSQS = _
  override def beforeAll = {
    sqs = new SQSService(8001, 123)
    sqs.start()
    val endpoint = new AwsClientBuilder.EndpointConfiguration("http://localhost:8001", "us-east-1")
    client = AmazonSQSClientBuilder.standard
      .withEndpointConfiguration(endpoint)
      .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials))
      .build
  }
  override def afterAll = {
    sqs.shutdown()
  }
}

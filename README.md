# SQS mock library for Java/Scala

[![Build Status](https://travis-ci.org/shuttie/sqsmock.svg?branch=master)](https://travis-ci.org/shuttie/sqsmock)

Sqsmock is a web service implementing AWS SQS API, which can be used for local testing of your code using SQS
but without hitting real SQS endpoints.

Implemented API methods:
* CreateQueue (supported params: VisibilityTimeout)
* DeleteMessage
* ReceiveMessage (supported params: MaxNumberOfMessages)
* SendMessage
* SendMessageBatch

## Installation

Sqsmock package is available for Scala 2.12 (on Java 8). To install using SBT, add these statements to your `build.sbt`:

    libraryDependencies += "io.mock.aws" %% "sqsmock" % "1.0.0" % "test"

On maven, update your `pom.xml` in the following way:

    // add this entry to <dependencies/>
    <dependency>
        <groupId>io.mock.aws</groupId>
        <artifactId>sqsmock_2.12</artifactId>
        <version>1.0.0</version>
        <scope>test</scope>
    </dependency>

## Usage
Scala:

    // create and start SQS API mock
    val api = new SQSService(port = 8001, account = 1)
    api.start()

    // AWS SQS client setup
    val endpoint = new AwsClientBuilder.EndpointConfiguration("http://localhost:8081", "us-east-1")
    val client = AmazonSQSClientBuilder
        .standard()
        .withEndpointConfiguration(endpoint)
        .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
        .build()

    // use it as usual
    val queue = client.createQueue("hello").getQueueUrl()
    client.sendMessage(queue, "world")

Java:

    // create and start SQS API mock
    SQSService api = new SQSService(8001, 1);
    api.start();

    // AWS SQS client setup
    AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("http://localhost:8081", "us-east-1");
    AmazonSQS client = AmazonSQSClientBuilder
        .standard()
        .withEndpointConfiguration(endpoint)
        .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
        .build();

    // use it as usual
    String queue = client.createQueue("hello").getQueueUrl();
    client.sendMessage(queue, "world");

## License

The MIT License (MIT)
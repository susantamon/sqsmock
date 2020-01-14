package io.mock.aws.sqsmock.messages

import io.mock.aws.sqsmock.model.Message

/**
  * Created by mondal on 1/14/2020
  */
case class GetQueueUrlResponse(message: Message) extends Response {
  def toXML =
    <GetQueueUrlResponse>
      <GetQueueUrlResult>
        <QueueUrl>{message.body}</QueueUrl>
      </GetQueueUrlResult>
      <ResponseMetadata>
        <RequestId>{uuid}</RequestId>
      </ResponseMetadata>
    </GetQueueUrlResponse>
}

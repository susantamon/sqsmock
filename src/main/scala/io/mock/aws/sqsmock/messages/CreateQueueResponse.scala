package io.mock.aws.sqsmock.messages

import java.util.UUID

import io.mock.aws.sqsmock.model.Queue

/**
  * Created by shutty on 3/29/16.
  */
case class CreateQueueResponse(queue:Queue) extends Response {
  def toXML =
    <CreateQueueResponse>
      <CreateQueueResult>
        <QueueUrl>{queue.url}</QueueUrl>
      </CreateQueueResult>
      <ResponseMetadata>
        <RequestId>{uuid}</RequestId>
      </ResponseMetadata>
    </CreateQueueResponse>
}

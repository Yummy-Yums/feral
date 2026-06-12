package feral.`google-cloud`.events

import io.circe.literal._
import munit.FunSuite

class MessagePublishedDataSuite extends FunSuite {

  import MessagePublishedDataSuite._

  test("decoder") {
    val data = event.as[MessagePublishedData].toTry.get
    assertEquals(data.subscription, "projects/my-project/subscriptions/my-subscription")
    assertEquals(data.message.data, "dGVzdCBtZXNzYWdlIDM=")
    assertEquals(data.message.attributes, Map("attr1" -> "attr1-value"))
    assertEquals(data.message.messageId, "message-id")
    assertEquals(data.message.publishTime, "2021-02-05T04:06:14.109Z")
  }

}

object MessagePublishedDataSuite {

  def event = json"""
        {
            "subscription": "projects/my-project/subscriptions/my-subscription",
            "message": {
                "attributes": {
                    "attr1":"attr1-value"
                },
                "data": "dGVzdCBtZXNzYWdlIDM=",
                "messageId": "message-id",
                "publishTime":"2021-02-05T04:06:14.109Z"
            }
        }
    """
}

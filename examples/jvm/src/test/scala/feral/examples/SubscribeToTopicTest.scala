package feral.examples

import io.circe.literal._
import io.cloudevents.core.builder.CloudEventBuilder

import java.util.Base64
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger

class SubscribeToTopicTest extends munit.FunSuite {
  val logger = Logger.getLogger("feral.examples.SubscribeToTopic")
  val testLogHandler = TestLogHandler()
  override def beforeAll(): Unit = {
    logger.addHandler(testLogHandler)
  }
  // the test uses plain json and base 64 to construct the encodedData unlike Protobuf example in StackDriverLoggingTests
  test("Google Cloud Function SubscribeToTopic should print pubsub message") {
    val msg = "Hello World"
    val encodedMessage = Base64.getEncoder().encodeToString(msg.getBytes())

    val encodedData =
      s"""{
          |"message": {
          |    "attributes": {
          |      "attr1": "attr1-value"
          |    },
          |    "data": "$encodedMessage",
          |    "messageId": "message-id",
          |    "publishTime": "2021-02-05T04:06:14.109Z"
          |  }
          |}""".stripMargin

    val event = CloudEventBuilder.v1()
      .withId("1234-5678-9012-3456")
      .withType("pubsub.message")
      .withSource(java.net.URI.create("https://github.com/cloudevents/spec/pull/123"))
      .withData(encodedData.getBytes())
      .build()

    new SubscribeToTopic().accept(event)

    val messages = testLogHandler.getLog

    val first_mesage = messages
        .filter(r => r.getMessage().contains("data"))
        .head
        .getMessage()
        .split(":")(1)
        .trim()

    val res = json"""{
      "message": {
          "attributes": {
              "attr1":"attr1-value"
          },
          "data": "SGVsbG8gV29ybGQ=",
          "messageId": "message-id",
          "publishTime":"2021-02-05T04:06:14.109Z"
        }
      }
    """
    
    val data = res.hcursor
        .downField("message")
        .downField("data")
        .focus
        .flatMap(_.asString)
        .get.trim()

    assertEquals(
      data,
      first_mesage
    )
  }
}

class TestLogHandler extends Handler {

    setLevel(Level.ALL)
    private val log_store: collection.mutable.ListBuffer[LogRecord] = scala.collection.mutable.ListBuffer.empty

    def getLog = log_store.result()
    def clear(): Unit = log_store.clear()
    def close(): Unit = ()
    def flush(): Unit = ()
    def publish(record: LogRecord): Unit = log_store += record
}

object TestLogHandler {
  def apply(): TestLogHandler = new TestLogHandler()
}
package feral.`google-cloud`
package events

import io.circe._

sealed abstract class MessagePublishedData {
  def message: PubsubMessage
  def subscription: String
  def deliveryAttempt: Option[Int]
}

object MessagePublishedData {

  def apply(
      message: PubsubMessage,
      subscription: String,
      deliveryAttempt: Option[Int] = None
  ): MessagePublishedData = {
    new Impl(message, subscription, deliveryAttempt)
  }

  implicit val decoder: Decoder[MessagePublishedData] =
    Decoder.forProduct3("message", "subscription", "deliveryAttempt")(
      MessagePublishedData.apply
    )

  private final case class Impl(
      message: PubsubMessage,
      subscription: String,
      deliveryAttempt: Option[Int]
  ) extends MessagePublishedData {
    override def productPrefix = "MessagePublishedData"
  }
}

sealed abstract class PubsubMessage {
  def data: String
  def attributes: Map[String, String]
  def messageId: String
  def publishTime: String
  def orderingKey: Option[String]
}

object PubsubMessage {

  def apply(
      data: String,
      attributes: Map[String, String],
      messageId: String,
      publishTime: String,
      orderingKey: Option[String]
  ): PubsubMessage = {
    new Impl(data, attributes, messageId, publishTime, orderingKey)
  }

  implicit val decoder: Decoder[PubsubMessage] = Decoder.forProduct5(
    "data",
    "attributes",
    "messageId",
    "publishTime",
    "orderingKey"
  )(PubsubMessage.apply)

  private final case class Impl(
      data: String,
      attributes: Map[String, String],
      messageId: String,
      publishTime: String,
      orderingKey: Option[String]
  ) extends PubsubMessage { override def productPrefix = "PubsubMessage" }
}

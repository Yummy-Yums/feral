package feral.examples

import cats.effect.IO
import cats.effect.Resource
import feral.googlecloud._
import feral.googlecloud.events._
import feral.lambda.INothing
import io.circe.Decoder

import java.util.logging.Logger

import SubscribeToTopic._

object SubscribeToTopic {

  sealed abstract class PubSubBody {
    def getMessage: PubsubMessage
  }

  object PubSubBody {
    def apply(message: PubsubMessage): PubSubBody = new Impl(message)

    implicit def decoder: Decoder[PubSubBody] = Decoder.forProduct1("message")(Impl.apply)

    private case class Impl(
        getMessage: PubsubMessage
    ) extends PubSubBody {
      override def productPrefix: String = "PubSubBody"
    }

  }

}

class SubscribeToTopic extends IOCloudEventsFunction[PubSubBody, INothing] {
  val logger = Logger.getLogger(this.getClass.getName())

  def handler: Resource[IO, ContextEventWithData[PubSubBody] => IO[Unit]] = {
    Resource.pure { event =>
      val msg = event.data.getMessage

      IO.pure {
        logger.info(s"Message ID: ${msg.messageId}")
        logger.info(s"Publish Time: ${msg.publishTime}")
        logger.info(s"Attributes: ${msg.attributes.mkString(", ")}")
        logger.info(s"data: ${msg.data.toBase64}")
      } >> IO.println("done")
    }
  }

}

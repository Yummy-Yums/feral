package feral.examples

import cats.effect.IO
import cats.effect.Resource
import feral.googlecloud._
import feral.lambda.INothing

import java.util.Base64
import java.util.logging.Logger

import SubscribeToTopic._

class StackDriverLogging extends IOCloudEventsFunction[PubSubBody, INothing] {

  def handler: Resource[IO, ContextEventWithData[PubSubBody] => IO[Unit]] = {
    val logger = Logger.getLogger(this.getClass.getName())

    Resource.pure { event =>
      val msg = event.data.getMessage.data

      val er = msg.toBase64

      if (!er.nonEmpty) {
        logger.info("Hello World")
        IO.unit
      } else {
        val decodedMessage = Base64.getDecoder().decode(er)
        val result = new String(decodedMessage)

        val output_message = s"Hello, $result"

        IO.pure {
          logger.info(s"data over the wire: ${output_message}")
        } >> IO.println("done")
      }

    }
  }
}

package scalajs.pulsar


import typings.node.global.Buffer
import typings.pulsarClient.mod.{Client, ClientOpts, Consumer, ProducerMessage, ProducerOpts, SubscribeOpts}

import scala.concurrent._
import ExecutionContext.Implicits.global

object PulsarApp {
  def main(args: Array[String]): Unit = {
    val clientOpts = ClientOpts("pulsar://localhost:6650")
    val client = new Client(clientOpts)

    val producerOpts = ProducerOpts(topic = "test")
    val producerF = client.createProducer(producerOpts).toFuture
    val msg: typings.node.Buffer = Buffer.from("Hello World")

    val subscribeOpts = SubscribeOpts(subscription = "my-subscription", topic = "test")

    val consumer = client.subscribe(subscribeOpts).toFuture

    val resultF = for {
      _ <- producerF.map(_.send(ProducerMessage(msg)))
      c <- consumer.map(_.receive())
      msg <- c.toFuture.map(_.getData())
    } yield {
      msg.toJSON()
    }

    resultF.map(d => println(d))

  }
}

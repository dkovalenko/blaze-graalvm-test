package example

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets.UTF_8

import org.http4s.blaze.http._
import org.http4s.blaze.http.HttpServerStageConfig
import org.http4s.blaze.http.http1.server.Http1ServerStage
import org.http4s.blaze.pipeline.LeafBuilder
import org.http4s.blaze.channel.SocketConnection
import org.http4s.blaze.channel.nio1.NIO1SocketServerGroup
import org.http4s.blaze.http.RouteAction._

import scala.concurrent.Future

case class Message(message: String)

object Main {
  private val config           = HttpServerStageConfig()
  private val plaintextHeaders = Seq("server" -> "blaze", "content-type" -> "text/plain")

  def serve(request: HttpRequest): Future[RouteAction] = Future.successful {
    request.url match {
      case "/plaintext" => Ok("Hello, World!".getBytes(UTF_8), plaintextHeaders)
      case path         => String(s"Not found: $path", 404, "Not Found", Nil)
    }
  }

  def connect(conn: SocketConnection): Future[LeafBuilder[ByteBuffer]] =
    Future.successful(LeafBuilder(new Http1ServerStage(serve, config)))

  def main(args: Array[String]): Unit =
    NIO1SocketServerGroup
      .fixedGroup()
      .bind(new InetSocketAddress(8081), connect)
      .getOrElse(sys.error("Failed to start server."))
      .join()

}
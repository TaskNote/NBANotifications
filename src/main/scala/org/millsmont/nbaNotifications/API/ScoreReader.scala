package org.millsmont.nbaNotifications.API

import org.millsmont.nbaNotifications.models.{APIResponse, LiveScoreReader, ScoreList}
import sttp.client._
import io.circe._
import io.circe.generic.auto._
import io.circe.generic.semiauto._
import io.circe.parser._
import io.circe.syntax._
import io.circe.generic.semiauto.deriveDecoder
import org.millsmont.nbaNotifications.JSON.Decoder._

trait ScoreReader {
  implicit val backend = HttpURLConnectionBackend()

  type GameId = Int



  //get all current live games. TODO: Fix Serializable to a real type
  def getLiveGames(): Either[Serializable, APIResponse] = {

    val request = basicRequest
      .get(uri"https://api-nba-v1.p.rapidapi.com/games/live/")
      .header("x-rapidapi-host", "api-nba-v1.p.rapidapi.com")
      .header("x-rapidapi-key", "")

    val response: Identity[Response[Either[String, String]]] = request.send()

    for {
      body <- response.body
      decoded <- parser.decode[APIResponse](body)

    } yield decoded

  }

  def getGameScoreDiff(id: GameId): Int = ???
}


object ReaderHackTest extends ScoreReader with App {

  val games: Either[Serializable, APIResponse] = this.getLiveGames()

  games match {
    case Left(err) => throw new RuntimeException(err.toString)
    case Right(games) => println(s"got live games: $games")
  }
}
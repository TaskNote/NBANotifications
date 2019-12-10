package org.millsmont.nbaNotifications.API

import java.time._

import org.millsmont.nbaNotifications.models.{APIResponse, GameData, GameList}
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
    println(response)

    for {
      body <- response.body
      decoded <- parser.decode[APIResponse](body)

    } yield decoded


  }

  // returns all games for today 
  def getTodayGames() = {

    val date = java.time.LocalDate.now(ZoneOffset.UTC).toString

    val request = basicRequest
      .get(uri"https://api-nba-v1.p.rapidapi.com/games/date/$date")
      .header("x-rapidapi-host", "api-nba-v1.p.rapidapi.com")
      .header("x-rapidapi-key", " ")


    val response: Identity[Response[Either[String, String]]] = request.send()
          println(response)   
    for {
      body <- response.body
      decoded <- parser.decode[APIResponse](body)

    } yield decoded

  }




  case class TimeRange(start: java.time.Instant, end: java.time.Instant)
  // grab two PST values: one to represent the start of all games today, one to
  // represent the time when the last one ends
  def getGameTimeRange(): Either[Serializable, TimeRange] = {

    val games: Either[Serializable, APIResponse] = this.getTodayGames()

    for {
      times <- games.map(_.api.games)
      minStart: String = times.map(_.startTime).min
      maxEnd: String = times.map(_.endTime).max

    } yield TimeRange(Instant.now(), Instant.now())
  }





  def getLiveCloseGames(): Either[Serializable, Seq[GameData]] = {

    val games: Either[Serializable, APIResponse] = this.getLiveGames()

    games.map(_.api.games.filter(_.diff < 6).filter(_.period == 4))
  }


}


  object ReaderHackTest extends ScoreReader with App {

    val games: Either[Serializable, Seq[GameData]] = this.getLiveCloseGames()
    val today = this.getTodayGames()

    println(today)

    games match {
      case Left(err) => throw new RuntimeException(err.toString)
      case Right(games) => println(s"got live games: $games")
    }
  }



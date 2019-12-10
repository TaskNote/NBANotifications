package org.millsmont.nbaNotifications.JSON

import org.millsmont.nbaNotifications.models.GameData
import io.circe._, io.circe.generic.auto._, io.circe.generic.semiauto._, io.circe.parser._, io.circe.syntax._, io.circe.generic.semiauto.deriveDecoder
import io.circe.Decoder

object Decoder {


  implicit val LiveScoreDecoder: Decoder[GameData] =
    (hCursor: HCursor) => {
      for {
        gameId <- hCursor.downField("gameId").as[Int]
        period <- hCursor.downField("currentPeriod").as[String]
        startTime <- hCursor.downField("startTimeUTC").as[String]
        endTime <- hCursor.downField("endTimeUTC").as[String]
        homePoints <- hCursor.downField("hTeam").downField("score").downField("points").as[Int]
        awayPoints <- hCursor.downField("vTeam").downField("score").downField("points").as[Int]
        awayTeam <- hCursor.downField("vTeam").downField("fullName").as[String]
        homeTeam <- hCursor.downField("hTeam").downField("fullName").as[String]
      } yield GameData(gameId, period.substring(0,1).toInt, startTime, endTime, homePoints, awayPoints, awayTeam, homeTeam)
    }

//  implicit val ListDecoder: Decoder[List[LiveScoreReader]] =
//    (hCursor: HCursor) => {
//      println(hCursor)
//      hCursor.downField("api").downField("games").as[List[LiveScoreReader]]
//    }




}

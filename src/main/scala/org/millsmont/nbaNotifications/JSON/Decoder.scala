package org.millsmont.nbaNotifications.JSON

import org.millsmont.nbaNotifications.models.LiveScoreReader
import io.circe._, io.circe.generic.auto._, io.circe.generic.semiauto._, io.circe.parser._, io.circe.syntax._, io.circe.generic.semiauto.deriveDecoder
import io.circe.Decoder

object Decoder {


  implicit val LiveScoreDecoder: Decoder[LiveScoreReader] =
    (hCursor: HCursor) => {
      for {
        gameId <- hCursor.downField("gameId").as[Int]
        homePoints <- hCursor.downField("hTeam").downField("score").downField("points").as[Int]
        awayPoints <- hCursor.downField("vTeam").downField("score").downField("points").as[Int]
      } yield LiveScoreReader(gameId, homePoints, awayPoints)
    }

//  implicit val ListDecoder: Decoder[List[LiveScoreReader]] =
//    (hCursor: HCursor) => {
//      println(hCursor)
//      hCursor.downField("api").downField("games").as[List[LiveScoreReader]]
//    }




}

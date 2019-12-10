package org.millsmont.nbaNotifications.models

import java.time.Instant

import scala.math.abs

// TODO consider making gameid a string
case class GameData(gameId: Int, period: Int, startTime: String, endTime: String,
                    homePoints: Int, awayPoints: Int, awayTeam: String, homeTeam: String) {

  val diff: Int = abs(homePoints - awayPoints)

}




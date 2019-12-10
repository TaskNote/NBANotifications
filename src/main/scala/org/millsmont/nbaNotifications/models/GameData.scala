package org.millsmont.nbaNotifications.models

import scala.math.abs

// TODO consider making gameid a string
case class LiveScoreReader(gameId: Int, period: Int, homePoints: Int, awayPoints: Int, awayTeam: String, homeTeam: String) {

  val diff = abs(homePoints - awayPoints)

}


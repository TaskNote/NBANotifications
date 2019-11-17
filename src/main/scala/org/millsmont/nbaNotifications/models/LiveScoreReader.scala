package org.millsmont.nbaNotifications.models

import scala.math.abs

// TODO consider making gameid a string
case class LiveScoreReader(gameId: Int, homePoints: Int, awayPoints: Int) {

  val diff = abs(homePoints - awayPoints)

}


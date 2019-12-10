package org.millsmont.nbaNotifications.models

import org.millsmont.nbaNotifications.models.LiveScoreReader._

case class ScoreList(games: Seq[LiveScoreReader])

case class APIResponse(api: ScoreList)

package org.millsmont.nbaNotifications.models

import org.millsmont.nbaNotifications.models.GameData._

case class GameList(games: Seq[GameData])

case class APIResponse(api: GameList)

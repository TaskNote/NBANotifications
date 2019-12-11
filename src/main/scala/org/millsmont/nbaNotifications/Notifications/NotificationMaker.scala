package org.millsmont.nbaNotifications.Notifications

import org.millsmont.nbaNotifications.API.GameReader
import com.twilio.Twilio
import com.twilio.`type`.PhoneNumber
import com.twilio.rest.api.v2010.account.Message
import com.typesafe.config.{Config, ConfigFactory}
import scala.io.StdIn.readLine


trait NotificationMaker extends GameReader {

  def sendCloseGames(): Unit = {

    // hard coded twilio values
    val config = ConfigFactory.load()

    val ACCOUNT_SID = config.getString("twilio.account_sid")
    val AUTH_TOKEN = config.getString("twilio.auth_token")

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN)

    val from = new PhoneNumber(config.getString("twilio.from_number"))
    val to = new PhoneNumber("+19093482181")

    // figure out what games are close at the moment
    val closeGameHeaders: String = getLiveCloseGames match {
      case Left (error) => "something went wrong"
      case Right (games) =>  games.map(_.gameHeader).mkString(" \n ")
    }

    val body = "Close games on right now: $closeGameHeaders"
    val message = Message.creator(to, from, body).create()

    println(s"confirmed message sent: $closeGameHeaders")
  }

}

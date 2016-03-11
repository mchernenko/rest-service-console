package controllers

import models._
import play.api.libs.json.Json
import play.api.mvc.{Action, BodyParsers, Controller}

object RESTServiceController extends Controller{

  def submitJobData = Action(BodyParsers.parse.json) { request =>
    request.body.validate[JobData].fold(
      errors => {
        BadRequest("Invalid data")
      },
      jobData => {
        WebSocketController.sendMessageToAll(jobData.toString)
        Ok("OK")
      }
    )
  }
}

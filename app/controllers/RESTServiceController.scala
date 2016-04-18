package controllers

import models._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

object RESTServiceController extends Controller{

  private val ACCESS_TOKEN = "jeoqRQl/yLSudhewBXF/e1PkJBpfxqv89LjlL/9DJAyFJq07FmtjVCMa84EBu0/V0T7ZYtcKE21h4CxKUIaVelOxFsDJUa8" +
    "kPr8lk1gyRrgGSRM+nTXpe+5s3KrSU54+17GFAmraDJa+rSjkpLWQ+b+rUaQMSom0qMVl6ZG9mlsSzctiw7cKNghY988TiARTpSF3ckpp4AzjB2" +
    "dvYrIG2SVa2lKQsvSvtt6jMCOYbF+WthQF7GJBnYMWgdyJmuPS6KC9M1fg1EOJjOeiZmIc1SouVxGuqUJ19CSKd6u5Ln27XQGGPOGHqjtaP5Dra" +
    "WxFY4MHM97Cxz5rOayit1NUz9RBnk8brcENkIHYIryhzV3NQUxHsGJ5F/8JUu7a1tbv"

  private val ACCESS_TOKEN_HEADER = "access-token";

  private val USERNAME = "qwerty"
  private val PASSWORD = "asdfgh"

  private val BAD_REQUEST_MESSAGE = "Invalid data"
  private val INCORRECT_CREDENTIALS_MESSAGE = "User not found"
  private val AUTHENTICATION_REQUIRED = "Authentication required"
  private val OK_MESSAGE = "OK"

  def submitJobData = Action(BodyParsers.parse.json) { request =>
    request.body.validate[JobData].fold(
      errors => {
        BadRequest(BAD_REQUEST_MESSAGE)
      },
      jobData => {
        WebSocketController.sendMessageToAll(jobData.toString)
        Ok(OK_MESSAGE)
      }
    )
  }

  def authenticate = Action(BodyParsers.parse.json) { implicit request =>
    request.body.validate[AuthenticateData].fold(
      errors => {
        BadRequest(BAD_REQUEST_MESSAGE)
      },
      authenticateData => {
        WebSocketController.sendMessageToAll("Authentication request received")
        WebSocketController.sendMessageToAll(authenticateData.toString)
        if(authenticateData.username == USERNAME && authenticateData.password == PASSWORD) {
          val json: JsValue = Json.obj(ACCESS_TOKEN_HEADER -> ACCESS_TOKEN)
          WebSocketController.sendMessageToAll("Sending access token")
          WebSocketController.sendMessageToAll(json.toString)
          Ok(json)
        }
        else {
          Forbidden(INCORRECT_CREDENTIALS_MESSAGE)
        }
      }
    )
  }

  def startJob = Action(BodyParsers.parse.json) { implicit request =>
    request.body.validate[StartJobData].fold(
      errors => {
        BadRequest(BAD_REQUEST_MESSAGE)
      },
      startJobData => {
        WebSocketController.sendMessageToAll("Start job request received")
        WebSocketController.sendMessageToAll(startJobData.toString)
        checkAuthentication
      }
    )
  }

  def endJob = Action(BodyParsers.parse.json) { implicit request =>
    request.body.validate[EndJobData].fold(
      errors => {
        BadRequest(BAD_REQUEST_MESSAGE)
      },
      endJobData => {
        WebSocketController.sendMessageToAll("End job request received")
        WebSocketController.sendMessageToAll(endJobData.toString)
        checkAuthentication
      }
    )
  }

  private def checkAuthentication(implicit request: Request[_]): Result = {
    request.headers.get(ACCESS_TOKEN_HEADER) match {
      case Some(accessToken) => {
        if(accessToken == ACCESS_TOKEN)
          Ok(OK_MESSAGE)
        else
          Forbidden(INCORRECT_CREDENTIALS_MESSAGE)
      }
      case None => Forbidden(AUTHENTICATION_REQUIRED);
    }
  }
}

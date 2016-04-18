
import play.api.http.Writeable
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

package object models {

  // JobData
  case class JobData(val jobId: Int, val pageCount: Int, val printerName: String) {
    override def toString = Json.toJson(this).toString()
  }

  implicit val jobDataWrites: Writes[JobData] = (
    (JsPath \ "jobId").write[Int] and
    (JsPath \ "pageCount").write[Int] and
    (JsPath \ "printerName").write[String]
    )(unlift(JobData.unapply))

  implicit val jobDataReads: Reads[JobData] = (
    (JsPath \ "jobId").read[Int] and
    (JsPath \ "pageCount").read[Int] and
    (JsPath \ "printerName").read[String]
    )(JobData.apply _)

  // AuthenticateData
  case class AuthenticateData(val username: String, val password: String) {
    override def toString = Json.toJson(this).toString()
  }

  implicit val authenticateDataWrites: Writes[AuthenticateData] = (
    (JsPath \ "UserName").write[String] and
    (JsPath \ "Password").write[String]
    )(unlift(AuthenticateData.unapply))

  implicit val authenticateDataReads: Reads[AuthenticateData] = (
    (JsPath \ "UserName").read[String] and
    (JsPath \ "Password").read[String]
    )(AuthenticateData.apply _)

  // StartJobData
  case class StartJobData(val ipAddress: String, val userName: String, val jobData: String) {
    override def toString = Json.toJson(this).toString()
  }

  implicit val startJobDataWrites: Writes[StartJobData] = (
    (JsPath \ "IPAddress").write[String] and
    (JsPath \ "UserName").write[String] and
    (JsPath \ "Jobdata").write[String]
    )(unlift(StartJobData.unapply))

  implicit val startJobDataReads: Reads[StartJobData] = (
    (JsPath \ "IPAddress").read[String] and
    (JsPath \ "UserName").read[String] and
    (JsPath \ "Jobdata").read[String]
    )(StartJobData.apply _)

  // EndJobData
  case class EndJobData(val ipAddress: String, val userName: String, val result: String) {
    override def toString = Json.toJson(this).toString()
  }

  implicit val endJobDataWrites: Writes[EndJobData] = (
    (JsPath \ "IPAddress").write[String] and
    (JsPath \ "UserName").write[String] and
    (JsPath \ "Result").write[String]
    )(unlift(EndJobData.unapply))

  implicit val endJobDataReads: Reads[EndJobData] = (
    (JsPath \ "IPAddress").read[String] and
    (JsPath \ "UserName").read[String] and
    (JsPath \ "Result").read[String]
    )(EndJobData.apply _)

}

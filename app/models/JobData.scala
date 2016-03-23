
import play.api.http.Writeable
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

package object models {

  case class JobData(val jobId: Int, val pageCount: Int, val printerName: String) {
    override def toString = Json.toJson(this).toString()
  }

  implicit val jobDataWrites: Writes[JobData] = (
      (JsPath \ "jobId").write[Int] and
      (JsPath \ "pageCount").write[Int] and
      (JsPath \ "printerName").write[String]
    )(unlift(JobData.unapply))

  implicit val placeReads: Reads[JobData] = (
    (JsPath \ "jobId").read[Int] and
    (JsPath \ "pageCount").read[Int] and
    (JsPath \ "printerName").read[String]
    )(JobData.apply _)


}

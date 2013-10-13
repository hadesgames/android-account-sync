package com.hadesgames.fbsync.util

import play.api.libs.json._
import play.api.libs.json.Writes._
import play.api.libs.functional._
import play.api.libs.functional.syntax._
import uk.co.bigbeeconsultants.http.header.CookieJar
import uk.co.bigbeeconsultants.http.header.Cookie
import uk.co.bigbeeconsultants.http.url.Domain
import uk.co.bigbeeconsultants.http.HttpDateTimeInstant

object Serializers {
  implicit val domainReads: Reads[Domain] =
      ((__ \ "domain").read[String]).map(new Domain(_))

  val domainUnapply: Domain => String = _.domain

  implicit val domainWrites: Writes[Domain]=
      ((__ \ "domain")).write[String].contramap(domainUnapply)


  implicit val altceva = Json.format[HttpDateTimeInstant]
  implicit val cookie = Json.format[Cookie]
  implicit val cookieJarReads: Reads[CookieJar] =
    ((__ \ "cookies").read[List[Cookie]]).map(new CookieJar(_))
  implicit val cookieJarWrites: Writes[CookieJar] =
    (__ \ "cookies").write[List[Cookie]].contramap((x: CookieJar) => x.cookies)
  //implicit val cookieJar = Json.format[Seq[Cookie]]

}

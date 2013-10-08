package com.hadesgames.fbsync.parsing

import uk.co.bigbeeconsultants.http.header.CookieJar
import com.hadesgames.fbsync.lib.HandlesErrors
import uk.co.bigbeeconsultants.http.toURL
import scala.xml.XML
import se.fishtank.css.selectors.Selectors._

case class PageMetadata(cookies: CookieJar)

case class Page(html: scala.xml.Elem, metadata: PageMetadata) {

}


object Page extends HandlesErrors{
  def apply(url: String): Option[Page] = {
    apply(url, CookieJar())
  }

  def apply(url: String, jar: CookieJar): Option[Page] = {

    val httpClient = Request.client

    for {
      response <- tryOrLog { httpClient.get(url, Nil, jar) }
      textHTML = response.body.asString
      parser = HTMLParser.tagsoup
      html <- tryOrLog { parser.loadString(textHTML) }
    } yield  {
      val metadata = new PageMetadata(response.cookies.getOrElse(CookieJar()))
      new Page(html, metadata)
    }
  }
}

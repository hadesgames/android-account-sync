package com.hadesgames.fbsync.parsing

import uk.co.bigbeeconsultants.http.header.{CookieKey, CookieJar}
import uk.co.bigbeeconsultants.http.request.RequestBody
import uk.co.bigbeeconsultants.http.toURL
import se.fishtank.css.selectors.Selectors._
import com.hadesgames.fbsync.util.HandlesErrors

object Strategies extends HandlesErrors{
  val loginURL = "https://m.facebook.com/login.php"

  def extractFormAction(page: Page): Option[String] = {
    val forms = page.html $ "form"
    if (forms.isEmpty)
      None
    else
      forms.head.attribute("action").map(_.text)
  }

  def authentinticate(email: String, password: String): Option[CookieJar] = {

    val credentials = RequestBody(Map(
      "email" -> email,
      "pass" -> password
    ))

    for {
      loginPage <- Page(loginURL)
      postURL <- extractFormAction(loginPage)
      response <- tryOrLog { Request.client.post(postURL, Some(credentials), Nil, loginPage.metadata.cookies) }
      jar <- response.cookies
    } yield {
      jar
    }

  }

  def getNumbers(user: String)(implicit jar: CookieJar): List[String] = {
    val url = s"https://m.facebook.com/$user?v=info"
    val numbersOpt = for {
      page <- Page(url, jar)
      mobileSpans = page.html $ "div[title=\"Mobile\"] span span"
    } yield {
      mobileSpans.map(_.text.replace(" ", ""))
    }
    numbersOpt.getOrElse(List())
  }
}

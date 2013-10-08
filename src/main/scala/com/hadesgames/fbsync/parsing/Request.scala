package com.hadesgames.fbsync.parsing

import uk.co.bigbeeconsultants.http.{HttpClient, Config}

object Request {
  val USER_AGENT = Some("Mozilla/4.01 (compatible; MSIE 6.0; Windows NT 5.1)")
  //val USER_AGENT = None //Some("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.76 Safari/537.36")

  val config = new Config(
    userAgentString = USER_AGENT
  )
  def client = {
    new HttpClient(config)
  }
}

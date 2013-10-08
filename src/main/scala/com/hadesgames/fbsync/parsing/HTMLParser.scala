package com.hadesgames.fbsync.parsing

import scala.xml.XML

object HTMLParser {
  def tagsoup = {
    XML.withSAXParser(new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl().newSAXParser())
  }

}

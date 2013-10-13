package com.hadesgames.fbsync.util

import org.scaloid.common.{LoggerTag, Logger}

trait HandlesErrors extends Logger {
  implicit val loggerTag = LoggerTag("FbSync")
  def tryOrLog[T](body: => T): Option[T] = {
    try {
      Some(body)
    } catch {
      case e: Throwable =>{
        error(e.toString)
        for (trace <- e.getStackTrace)
          error(trace.toString)
        None
      }
    }
  }

}

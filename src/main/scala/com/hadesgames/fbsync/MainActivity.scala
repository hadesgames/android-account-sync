package com.hadesgames.fbsync

import play.api.libs.json._
import android.os.Bundle
import scala.concurrent.ExecutionContext.Implicits.global
import org.scaloid.common._
import com.hadesgames.fbsync.parsing.Strategies
import scala.concurrent.future
import android.widget.{EditText, Button, TextView, LinearLayout}
import org.macroid._
import android.support.v4.app.FragmentActivity
import org.macroid.contrib.Layouts.VerticalLinearLayout
import uk.co.bigbeeconsultants.http.header.CookieJar
import com.hadesgames.fbsync.tweaks.EditTextTweaks._
import com.hadesgames.fbsync.tweaks.TextTypes
import com.hadesgames.fbsync.activities.FacebookLoginActivity

import com.hadesgames.fbsync.util.HandlesErrors
import com.hadesgames.fbsync.util.Serializers._
import scala.Some
import org.scaloid.common.LoggerTag

class MainActivity extends SActivity with LayoutDsl with Tweaks with HandlesErrors{
  override implicit val loggerTag = LoggerTag("FbSync")
  onCreate {
    setContentView(mainView)
    pref = Preferences()
  }

  onStart {

    val result = tryOrLog {
      val strData: String = pref.auth("")
      Json.fromJson[CookieJar](Json.parse(strData)).asOpt
    }.flatMap(identity)

    auth = result
  }

  var pref: Preferences = _
  var emailSlot = slot[EditText]
  var passSlot = slot[EditText]

  var auth: Option[CookieJar] = None
  /*
  def authenticate {
    for  {
      email <- emailSlot
      pass <- passSlot
    } {
      future {
        auth = Strategies.authentinticate(email.getText.toString, pass.getText.toString)
      }
    }
  }*/

  def authenticate {
    toast(Preferences().auth(""))
  }

  def getSergey {
    auth match {
      case None => toast("No authentication found")
      case Some(jar) => future {
        val numbers = Strategies.getNumbers("rudolfovic")(jar)
        runOnUiThread(toast(numbers.toString))
      }
    }
  }

  def switch {
    startActivity(SIntent[FacebookLoginActivity])
  }

  def logout {
    Preferences().auth = ""
  }

  def mainView = {
    l[VerticalLinearLayout](
      w[Button]
        ~> text("Log in to Facebook")
        ~> On.click(switch),
      w[Button]
        ~> text("Delete log in")
        ~> On.click(logout),

      w[Button]
        ~> text("Print Authenticate")
        ~> On.click(authenticate),
      w[Button]
        ~> text("Get Sergey")
        ~> On.click(getSergey)

    )
  }

}

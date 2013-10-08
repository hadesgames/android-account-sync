package com.hadesgames.fbsync

import android.os.Bundle
import scala.concurrent.ExecutionContext.Implicits.global
import org.scaloid.common._
import com.hadesgames.fbsync.lib.Parser
import com.hadesgames.fbsync.parsing.Strategies
import scala.concurrent.future
import android.app.Activity
import android.widget.{EditText, Button, TextView, LinearLayout}
import org.macroid._
import android.support.v4.app.FragmentActivity
import org.macroid.contrib.Layouts.VerticalLinearLayout
import uk.co.bigbeeconsultants.http.header.CookieJar
import com.hadesgames.fbsync.tweaks.EditTextTweaks._
import com.hadesgames.fbsync.tweaks.TextTypes


class MainActivity extends FragmentActivity with FullDslActivity{
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(mainView)
  }

  var emailSlot = slot[EditText]
  var passSlot = slot[EditText]

  var auth: Option[CookieJar] = None

  def authenticate {
    for  {
      email <- emailSlot
      pass <- passSlot
    } {
      future {
        auth = Strategies.authentinticate(email.getText.toString, pass.getText.toString)
      }
    }
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
  def mainView = {
    l[VerticalLinearLayout](
      w[EditText]
        ~> inputType(TextTypes.textEmailAddress)
        ~> wire(emailSlot),

      w[EditText]
        ~> inputType(TextTypes.textPassword)
        ~> wire(passSlot),

      w[Button]
        ~> text("Login")
        ~> On.click(authenticate),

      w[Button]
        ~> text("Get Sergey")
        ~> On.click(getSergey)

    )
  }

}

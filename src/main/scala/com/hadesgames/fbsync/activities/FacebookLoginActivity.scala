package com.hadesgames.fbsync.activities

import play.api.libs.json.Json
import org.scaloid.common.{Preferences, SActivity}
import org.macroid.{Tweaks, FullDslActivity, LayoutDsl}
import android.support.v4.app.FragmentActivity
import org.macroid.contrib.Layouts.VerticalLinearLayout
import android.widget.{Button, EditText}
import com.hadesgames.fbsync.tweaks.TextTypes
import com.hadesgames.fbsync.tweaks.EditTextTweaks._
import com.hadesgames.fbsync.parsing.Strategies
import scala.concurrent.future
import org.scaloid.common._
import scala.concurrent.ExecutionContext.Implicits.global
import com.hadesgames.fbsync.util.HandlesErrors
import uk.co.bigbeeconsultants.http.header.CookieJar

import com.hadesgames.fbsync.util.Serializers._

class FacebookLoginActivity extends SActivity with LayoutDsl with Tweaks with HandlesErrors{
  override implicit val loggerTag = LoggerTag("FbSync")
  var emailSlot = slot[EditText]
  var passSlot = slot[EditText]
  onCreate {
    setContentView(mainView)
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
        ~> On.click(authenticate)

    )
  }

  def authenticate {
    for  {
      email <- emailSlot
      pass <- passSlot
    } {
      future {
        tryOrLog {
        val auth = Strategies.authentinticate(email.getText.toString, pass.getText.toString)
        val pref = Preferences()


        pref.auth = Json.stringify(Json.toJson[Option[CookieJar]](auth))
        finish()
        }
      }
    }
  }
}

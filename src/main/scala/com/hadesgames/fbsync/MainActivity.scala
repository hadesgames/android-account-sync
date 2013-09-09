package com.hadesgames.fbsync

import android.os.Bundle
import org.scaloid.common._

class MainActivity extends SActivity {
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    val view = new SVerticalLayout() {
      STextView("Hello world")
    }

    setContentView(view)
  }

}

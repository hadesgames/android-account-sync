package com.hadesgames.fbsync.tweaks

import android.widget.{TextView, EditText}
import org.macroid.LayoutDsl.Tweak
import android.text.InputType._

// Constants are taken from http://developer.android.com/reference/android/widget/TextView.html#attr_android:inputType
object TextTypes {
    val none = 0x00000000
    val text = 0x00000001
    val textCapCharacters = 0x00001001
    val textCapWords = 0x00002001
    val textCapSentences = 0x00004001
    val textAutoCorrect = 0x00008001
    val textAutoComplete = 0x00010001
    val textMultiLine = 0x00020001
    val textImeMultiLine = 0x00040001
    val textNoSuggestions = 0x00080001
    val textUri = 0x00000011
    val textEmailAddress = 0x00000021
    val textEmailSubject = 0x00000031
    val textShortMessage = 0x00000041
    val textLongMessage = 0x00000051
    val textPersonName = 0x00000061
    val textPostalAddress = 0x00000071
    val textPassword = 0x00000081
    val textVisiblePassword = 0x00000091
    val textWebEditText = 0x000000
    val textFilter = 0x000000
    val textPhonetic = 0x000000
    val textWebEmailAddress = 0x000000
    val textWebPassword = 0x000000
    val number = 0x00000002
    val numberSigned = 0x00001002
    val numberDecimal = 0x00002002
    val numberPassword = 0x00000012
    val phone = 0x00000003
    val datetime = 0x00000004
    val date = 0x00000014
    val time = 0x00000024
}

object EditTextTweaks {
  def inputType(t: Int): Tweak[TextView] = _.setInputType(t)
}

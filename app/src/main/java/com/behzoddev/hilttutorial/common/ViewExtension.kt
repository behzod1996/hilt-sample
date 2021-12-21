package com.behzoddev.hilttutorial.common

import android.content.Context
import android.widget.Toast

fun Context.toastLong(msg: String) {
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}
fun Context.toastShort(msg: String) {
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}
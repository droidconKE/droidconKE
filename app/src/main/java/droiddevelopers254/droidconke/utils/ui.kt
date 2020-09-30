package droiddevelopers254.droidconke.utils

import android.content.Context
import android.widget.Toast

// This file holds UI - related extension functions

fun Context.toast(message: String) {
  Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
  Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
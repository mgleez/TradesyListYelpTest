package com.mgleez.tradesylistyelptest.utils

import android.content.Context
import android.widget.Toast

/**
 * Toast to display the error.
 */
fun String.toast(context: Context, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, this, length).show()
}

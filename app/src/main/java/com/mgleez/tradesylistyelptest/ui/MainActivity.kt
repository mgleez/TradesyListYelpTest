package com.mgleez.tradesylistyelptest.ui

import android.app.Activity
import android.os.Bundle
import com.mgleez.tradesylistyelptest.R

/**
 * MainActivity
 *
 * Presents a list of businesses based on the user's input in the search field.
 * Each business item in the list presents: its name, image, and top review.
 *
 * Created by Mike Margulies 20210224
 */
class MainActivity: Activity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }
}
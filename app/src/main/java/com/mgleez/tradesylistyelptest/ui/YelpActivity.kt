package com.mgleez.tradesylistyelptest.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.mgleez.tradesylistyelptest.R
import com.mgleez.tradesylistyelptest.models.YelpSearch
import com.mgleez.tradesylistyelptest.utils.ViewModelIntent
import com.mgleez.tradesylistyelptest.utils.hide
import com.mgleez.tradesylistyelptest.utils.show
import com.mgleez.tradesylistyelptest.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Presents a list of businesses based on the user's input in the search field.
 * Each business item in the list presents: its name, image, and top review.
 *
 * Based on MVI, YelpActivity subscribes to observe the YelpSearchViewModel, handles the observed
 * viewModelIntents, and sets the vewModel's initial intent to the GetYelpEvent.
 *
 * With the @AndroidEntryPoint annotation (with the Hilt Gradle Plugin), a YelpActivity base class
 * is generated where the RetroModule, RoomModule, and RepositoryModule (the Dagger components, the
 * (@InstallIn(ApplicationComponent::class) @Module annotated objects)) are instantiated.
 *
 * YelpSearchViewModel annotated with @ViewModelInject is available via "by viewModels()"
 *
 * Created by Mike Margulies 20210224
 */
@AndroidEntryPoint
class YelpActivity : AppCompatActivity() {

  private val viewModel: YelpSearchViewModel by viewModels()

  /**
   * Subscribes to observe the YelpSearchViewModel and handles the observed viewModelIntents by:
   * - starting the progress bar when loading starts
   * - stopping the progress bar when loading completes or errors
   * - toasting any error message
   * Request businesses can be done from the YelpSearchViewModel with YelpSearchIntent.GetYelpEvent.
   */
  @ExperimentalCoroutinesApi // Experimental launchIn() is used in viewModel.setYelpSearchViewModelIntent()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    // onViewModelIntent
    viewModel.yelpSearchViewModelIntent.observe(this, {
      when (it) {
        is ViewModelIntent.Loading             -> progressBar.show()
        is ViewModelIntent.Success<YelpSearch> -> progressBar.hide()
        is ViewModelIntent.Error               -> {
          progressBar.hide()
          it.exception.message?.toast(this)
        }
      }
    })
  }
}

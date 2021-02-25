package com.mgleez.tradesylistyelptest.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
@ExperimentalCoroutinesApi // Experimental launchIn() is used in viewModel.setYelpSearchViewModelIntent()
class YelpActivity : AppCompatActivity() {
  private val viewModel: YelpSearchViewModel by viewModels()

  /**
   * Subscribes to observe the YelpSearchViewModel and handles the observed viewModelIntents by:
   * - starting the progress bar when loading starts
   * - stopping the progress bar when loading completes or errors
   * - toasting any error message
   * Request businesses can be done from the YelpSearchViewModel with YelpSearchIntent.GetYelpEvent.
   */
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
    intent.onSearchActionIntent()
  }

  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
    setIntent(intent)
    intent.onSearchActionIntent()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the options menu from XML
    menuInflater.inflate(R.menu.options_menu, menu)

    // Get the SearchView and set the searchable configuration
    (menu.findItem(R.id.menu_search).actionView as SearchView).apply {
      // Enable assisted search. Get a reference to the SearchableInfo.
      // Assumes current activity (componentName) is the searchable activity
      setSearchableInfo((getSystemService(Context.SEARCH_SERVICE) as SearchManager).getSearchableInfo(componentName))
      isIconifiedByDefault = false // Do not iconify the widget; expand it by default
    }
    return true
  }

  private fun Intent?.onSearchActionIntent() {
    if (this?.action == Intent.ACTION_SEARCH) {
      getStringExtra(SearchManager.QUERY)?.also { query ->
        viewModel.setYelpSearchViewModelIntent(YelpSearchIntent.GetYelpSearchEvent, query)
      }
    }
  }

}
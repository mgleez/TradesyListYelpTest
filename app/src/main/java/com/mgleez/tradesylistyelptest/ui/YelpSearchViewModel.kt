package com.mgleez.tradesylistyelptest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgleez.tradesylistyelptest.models.YelpReviewList
import com.mgleez.tradesylistyelptest.models.YelpSearch
import com.mgleez.tradesylistyelptest.repository.YelpRepository
import com.mgleez.tradesylistyelptest.utils.ViewModelIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * YelpSearchViewModel is a class holding the yelp search data used by any observing UI.
 *
 * It extends [ViewModel] so it can be used:
 * - in an Activity with "YelpSearchViewModel by viewModels()"
 *
 * or
 * - in a Fragment with  "YelpSearchViewModel by activityViewModels()"
 *
 * Identified as a viewModel for injection (by the @[HiltViewModel] and @[Inject] annotations),
 * YelpSearchViewModel's constructor's dependency, YelpSearchRepository, is injected by
 * Dagger's Hilt YelpSearchRepository made available to be injected into the YelpSearchViewModel
 * using @InstallIn on RepositoryModule and an @Provides method that returns YelpSearchRepository.
 *
 * @param repository is the injected YelpSearchRepository.
 *
 * @author Michael Philip Margulies 20210224
 */
@HiltViewModel
class YelpSearchViewModel
@Inject
constructor(
  private val repository: YelpRepository,
  private val saveStateHandle: SavedStateHandle
) : ViewModel() {
  private val _yelpSearchViewModelIntent: MutableLiveData<ViewModelIntent<YelpSearch>> = MutableLiveData()
  val yelpSearchViewModelIntent: LiveData<ViewModelIntent<YelpSearch>>
    get() = _yelpSearchViewModelIntent
  private val _yelpReviewListViewModelIntent: MutableLiveData<ViewModelIntent<YelpReviewList>> = MutableLiveData()
  val yelpReviewListViewModelIntent: LiveData<ViewModelIntent<YelpReviewList>>
    get() = _yelpReviewListViewModelIntent

  /**
   * Set viewModelIntent. Interpret and handle event
   */
  @ExperimentalCoroutinesApi // Experimental launchIn()
  fun setYelpViewModelIntent(viewModelSearchIntent: YelpSearchIntent, id: String) {
    viewModelScope.launch {
      when (viewModelSearchIntent) {
        is YelpSearchIntent.GetEventListYelpReview   -> {
          repository.getYelpReviewIntentFlow(id)
            .onEach {
              _yelpReviewListViewModelIntent.value = it
            }
            .launchIn(viewModelScope)
        }
        is YelpSearchIntent.GetEventListYelpBusiness -> {
          repository.getYelpSearchIntentFlow(id)
            .onEach {
              _yelpSearchViewModelIntent.value = it
            }
            .launchIn(viewModelScope)
        }
        is YelpSearchIntent.None                     -> {
          // not used
        }
      }
    }
  }
}

/**
 * This sealed class's objects are the possible YelpSearch intents used to communicate an event.
 */
sealed class YelpSearchIntent {
  object GetEventListYelpBusiness : YelpSearchIntent()
  object GetEventListYelpReview : YelpSearchIntent()
  object None : YelpSearchIntent()
}



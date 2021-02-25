package com.mgleez.tradesylistyelptest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Yelp base url
 */
const val BASE_URL = "https://api.yelp.com/v3/"

fun getYelpApiKey() = "Bearer eWe45X-6OvoTp2pjmi2TzoZ242tg4JafpCATbw1NAschYTetIM5V6oSxqxL-gWT1OrNN2idb5GVxLMtgQ0nj0p9E9OsX8IsqMo9Cjakppvl6Bn8eigUtUUtOlWCgX3Yx"
var currentLatitude: Double = 34.0928
var currentLongitude: Double = 118.3287
var currentLocation: String? = null
var currentSearchTerm: String = ""
var isPlaceRejected = false

/**
 * TradesyListYelpTest: YelpApp
 *
 * Client ID
 * IfJ2k_cEsEZt4vpgVOmcjg
 * API Key
 * eWe45X-6OvoTp2pjmi2TzoZ242tg4JafpCATbw1NAschYTetIM5V6oSxqxL-gWT1OrNN2idb5GVxLMtgQ0nj0p9E9OsX8IsqMo9Cjakppvl6Bn8eigUtUUtOlWCgX3Yx
 *
 * This application uses @HiltAndroidApp (with the Hilt Gradle Plugin) to trigger Dagger component
 * generation and to generate a base class to this Application class. A base class where the Dagger
 * components (@InstallIn(SingletonComponent::class) @Module annotated objects) are instantiated
 * at the right point in the lifecycle. A base class that injects members (made available to be
 * injected into a viewModel annotated with @ViewModelInject) into @AndroidEntryPoint annotated
 * Android classes: YelpActivity, etc.
 *
 * - Presents a list of businesses based on the user's input in the search field.
 * - Each business item in the list presents: its name and image.
 * - The results should be relevant to the user’s current location.
 * - The user should be able to enter a new search term and start a new search.
 *
 * - Unit Tests important functionality
 *
 * Maybe:
 * - Implementing Autocomplete
 * - Implementing a Result Detail Page
 * - Implement searching from a different location than your current
 *
 * Created by Mike Margulies 20210224
 */
@HiltAndroidApp
class YelpApp : Application()

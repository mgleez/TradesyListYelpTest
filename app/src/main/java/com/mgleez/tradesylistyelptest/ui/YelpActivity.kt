package com.mgleez.tradesylistyelptest.ui

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.SearchView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mgleez.tradesylistyelptest.*
import com.mgleez.tradesylistyelptest.models.YelpSearch
import com.mgleez.tradesylistyelptest.utils.ViewModelIntent
import com.mgleez.tradesylistyelptest.utils.hide
import com.mgleez.tradesylistyelptest.utils.show
import com.mgleez.tradesylistyelptest.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_textview.view.*
import kotlinx.android.synthetic.main.spinner_place.*
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
 * @author Michael Philip Margulies 20210224
 */
@AndroidEntryPoint
@ExperimentalCoroutinesApi // Experimental launchIn() is used in viewModel.setYelpSearchViewModelIntent()
class YelpActivity : AppCompatActivity() {
  private val viewModel: YelpSearchViewModel by viewModels()
  private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
  private lateinit var fusedLocationClient: FusedLocationProviderClient
  private lateinit var adapterPlace: PlaceSpinnerAdapter
  lateinit var places: Array<String>
  lateinit var spinnerItem: String
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
    places = resources.getStringArray(R.array.places)
    viewModel.yelpSearchViewModelIntent.observe(this, {
      when (it) {
        is ViewModelIntent.Loading -> {
          progressBar.show()
          search.text = currentSearchTerm
        }
        is ViewModelIntent.Success<YelpSearch> -> {
          progressBar.hide()
        }
        is ViewModelIntent.Error -> {
          progressBar.hide()
          it.exception.message?.toast(this)
        }
      }
    })
    intent.onSearchActionIntent()
    // Register the permissions callback
    adapterPlace = PlaceSpinnerAdapter(this)
    requestPermissionLauncher =
      registerForActivityResult(
        ActivityResultContracts.RequestPermission()
      ) { isGranted: Boolean ->
        when {
          isGranted -> requestLocationPermission()
          !isPlaceRejected -> requestPlace()
        }
      }
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    requestLocationPermission()
  }

  private fun requestPlace() {
    Dialog(this).also { dialog ->
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      dialog.setContentView(R.layout.spinner_place)
      dialog.setCancelable(true)
      // init the location dialog
      dialog.placeSpinner.adapter = adapterPlace
      dialog.placeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
          parent: AdapterView<*>?,
          view: View,
          position: Int,
          id: Long
        ) {
          spinnerItem = places[position]
          dialog.placeEditText.setText(spinnerItem)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
      }
      dialog.placeOkButton.setOnClickListener {
        dialog.dismiss()
        currentLocation = dialog.placeEditText.text.toString().trim { it <= ' ' }
      }
      dialog.placeCancelButton.setOnClickListener {
        dialog.dismiss()
        if (!isPlaceRejected) {
          requestLocationPermission()
        }
        isPlaceRejected = true
      }
    }.show()
  }

  private fun requestLocationPermission(){
    when {
      ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
              == PackageManager.PERMISSION_GRANTED -> {
        fusedLocationClient.lastLocation
          .addOnSuccessListener { location : Location? ->
            currentLatitude = location?.latitude ?: currentLatitude
            currentLongitude = location?.longitude ?: currentLongitude
            currentLocation = null
            "Using $currentLatitude, $currentLongitude".toast(this)
          }
      }
      shouldShowRequestPermissionRationale("ACCESS_COARSE_LOCATION") -> {
        AlertDialog.Builder(this).apply {
          setTitle(getString(R.string.enableLocation))
          setMessage(getString(R.string.enableLocationMessage))
          setPositiveButton("Ok") { _, _ ->
            requestPermissionLauncher.launch(
              Manifest.permission.ACCESS_COARSE_LOCATION
            )
          }
        }.show()
      }
      else -> {
        // Directly ask for the permission.
        // The registered ActivityResultCallback gets the result of this request.
        requestPermissionLauncher.launch(
          Manifest.permission.ACCESS_COARSE_LOCATION
        )
      }
    }
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

  /**
   * Handles the ACTION_SEARCH intent with the GetYelpSearchEvent.
   */
  private fun Intent?.onSearchActionIntent() {
    if (this?.action == Intent.ACTION_SEARCH) {
      getStringExtra(SearchManager.QUERY)?.also { query ->
        viewModel.setYelpViewModelIntent(YelpSearchIntent.GetEventListYelpBusiness, query)
        search.text = query
      }
    }
  }

  /**
   * Presents the string array of places.
   */
  inner class PlaceSpinnerAdapter(private var context: Context) : BaseAdapter() {
    override fun getCount(): Int = places.size
    override fun getItem(position: Int): Any = position
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View = convertView.addText(position)

    /**
     * Adds the text (inflates the view if needed).
     */
    private fun View?.addText(position: Int) =
      (this ?: run { // no view so inflate it
        (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.item_textview, null)
      }).apply { textView.text = places[position] }
  }

}
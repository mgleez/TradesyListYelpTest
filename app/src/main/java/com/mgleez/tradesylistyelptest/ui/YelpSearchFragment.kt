package com.mgleez.tradesylistyelptest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mgleez.tradesylistyelptest.R
import com.mgleez.tradesylistyelptest.models.YelpBusiness
import com.mgleez.tradesylistyelptest.models.YelpReviewList
import com.mgleez.tradesylistyelptest.models.YelpSearch
import com.mgleez.tradesylistyelptest.utils.RecyclerViewDecorationPadding
import com.mgleez.tradesylistyelptest.utils.ViewModelIntent
import com.mgleez.tradesylistyelptest.utils.toast
import kotlinx.android.synthetic.main.fragment_yelp_search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Using the MVI pattern, request yelp search results.
 *
 * Created by Mike Margulies 20210224
 */
class YelpSearchFragment : Fragment() {
  private val viewModel: YelpSearchViewModel by activityViewModels()
  private lateinit var yelpBusinessRecyclerListAdapter: YelpBusinessRecyclerListAdapter
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_yelp_search, container, false)

  /**
   * Initializes the RecyclerView's decoration and adapter.
   */
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // viewInteractionInit: RecyclerView
    yelpBusinessRecyclerView.layoutManager = LinearLayoutManager(this.context)
    yelpBusinessRecyclerView.addItemDecoration(RecyclerViewDecorationPadding(8))
    yelpBusinessRecyclerListAdapter = YelpBusinessRecyclerListAdapter(
      object : YelpBusinessRecyclerListAdapter.YelpBusinessViewHolder.Interaction {
        override fun onItemSelected(position: Int, item: YelpBusiness) {
          context?.let { "Business: ${item.name}".toast(it) }
        }
      })
    yelpBusinessRecyclerView.adapter = yelpBusinessRecyclerListAdapter
  }

  @ExperimentalCoroutinesApi
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    var reviewRequestCount = 0
    viewModel.yelpSearchViewModelIntent.observe(viewLifecycleOwner, {
      // onViewModelIntent: Success
      when (it) {
        is ViewModelIntent.Success<YelpSearch> -> {
          it.data.yelpBusinessList.forEach { business ->
            viewModel.setYelpReviewListViewModelIntent(YelpReviewListIntent.GetYelpReviewListEvent, business.id)
            reviewRequestCount--
          }
          this.yelpBusinessRecyclerListAdapter.submitList(
            it.data.yelpBusinessList.sortedBy { business -> business.name }
          )
        }
        is ViewModelIntent.Error               -> reviewRequestCount--
        ViewModelIntent.Loading                -> reviewRequestCount++
      }
    })
    viewModel.yelpReviewListViewModelIntent.observe(viewLifecycleOwner, {
      // onViewModelIntent: Success
      if (it is ViewModelIntent.Success<YelpReviewList>) {
        if (reviewRequestCount <= 0) {
          this.yelpBusinessRecyclerListAdapter.submitList(it.data.businessList.sortedBy { business -> business.name })
          this.yelpBusinessRecyclerListAdapter.notifyDataSetChanged()
        }
      }
    })
  }
}
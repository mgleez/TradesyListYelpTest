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
import com.mgleez.tradesylistyelptest.models.YelpSearch
import com.mgleez.tradesylistyelptest.utils.RecyclerViewDecorationPadding
import com.mgleez.tradesylistyelptest.utils.ViewModelIntent
import com.mgleez.tradesylistyelptest.utils.toast
import kotlinx.android.synthetic.main.fragment_yelp_search.*

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
//          detailTime.text = item.time.toString()
//          detailDate.text = item.time.toString()
        }
      })
    yelpBusinessRecyclerView.adapter = yelpBusinessRecyclerListAdapter
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel.yelpSearchViewModelIntent.observe(viewLifecycleOwner, {
      // onViewModelIntent: Success
      if (it is ViewModelIntent.Success<YelpSearch>) {
        this.yelpBusinessRecyclerListAdapter.submitList(
          it.data.yelpBusinessList
        )
      }
    })
  }
}
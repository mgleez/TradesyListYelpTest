package com.mgleez.tradesylistyelptest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mgleez.tradesylistyelptest.R
import com.mgleez.tradesylistyelptest.models.YelpSearch
import com.mgleez.tradesylistyelptest.utils.ViewModelIntent
import kotlinx.android.synthetic.main.fragment_yelp_search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Using the MVI pattern, request yelp search results.
 *
 * Created by Mike Margulies 20210224
 */
class YelpSearchFragment : Fragment() {

    private val viewModel: YelpSearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_yelp_search, container, false)

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // viewInteractionInit: setOnClickListener
        submit.setOnClickListener {
            // TODO move to fragment's submit
            viewModel.setYelpSearchViewModelIntent(YelpSearchIntent.GetYelpSearchEvent)
            // NavHostFragment.findNavController(this).navigate(R.id.businessListFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.yelpSearchViewModelIntent.observe(viewLifecycleOwner, {
            // onViewModelIntent: Success
            if (it is ViewModelIntent.Success<YelpSearch>) {
                count.text = it.data.yelpBusinessList.size.toString()
            }
        })
    }
}
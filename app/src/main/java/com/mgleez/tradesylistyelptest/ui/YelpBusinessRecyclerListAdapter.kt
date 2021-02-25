package com.mgleez.tradesylistyelptest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mgleez.tradesylistyelptest.R
import com.mgleez.tradesylistyelptest.models.YelpBusiness
import kotlinx.android.synthetic.main.item_business.view.*

// Based on Mitch Tabian's template
/**
 * Adapter using DiffUtil for the business list, with handler of user on item
 * interactions like click and, maybe later, swipe.
 *
 * Created by Mike Margulies 20210224
 */
class YelpBusinessRecyclerListAdapter(
  private val interaction: YelpBusinessViewHolder.Interaction? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  // Not using ListAdapter; using AsyncListDiffer to create a simpler interface
  private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<YelpBusiness>() {
    override fun areItemsTheSame(o: YelpBusiness, n: YelpBusiness) = o.id == n.id
    override fun areContentsTheSame(o: YelpBusiness, n: YelpBusiness) = o == n
  })

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): RecyclerView.ViewHolder = YelpBusinessViewHolder(
    LayoutInflater
      .from(parent.context)
      .inflate(R.layout.item_business, parent, false),
    interaction
  )

  override fun onBindViewHolder(
    holder: RecyclerView.ViewHolder,
    position: Int
  ) {
    when (holder) {
      is YelpBusinessViewHolder -> {
        // This is where we get the list from the differ
        holder.bind(differ.currentList[position])
      }
    }
  }

  override fun getItemCount(): Int = differ.currentList.size
  fun submitList(list: List<YelpBusiness>) = differ.submitList(list)
  class YelpBusinessViewHolder
  constructor(
    itemView: View,
    private val interaction: Interaction? // interface for clicks
  ) : RecyclerView.ViewHolder(itemView) {
    fun bind(yelpBusiness: YelpBusiness) = with(itemView) {
      setOnClickListener {
        interaction?.onItemSelected(adapterPosition, yelpBusiness)
      }
      businessImage.let {
        Glide.with(it.context)
          .applyDefaultRequestOptions(
            RequestOptions()
              .placeholder(R.drawable.ic_launcher_background)
              .error(R.drawable.ic_launcher_background)
          )
          .load(yelpBusiness.image)
          .centerCrop()
          .into(it)
      }
      businessRating.setImageResource(
        yelpBusiness.rating?.let {
          when {
            it >= 5.0 -> R.drawable.stars_regular_5
            it >= 4.5 -> R.drawable.stars_regular_4_half
            it >= 4.0 -> R.drawable.stars_regular_4
            it >= 3.5 -> R.drawable.stars_regular_3_half
            it >= 3.0 -> R.drawable.stars_regular_3
            it >= 2.5 -> R.drawable.stars_regular_2_half
            it >= 2.0 -> R.drawable.stars_regular_2
            it >= 1.5 -> R.drawable.stars_regular_1_half
            it >= 1.0 -> R.drawable.stars_regular_1
            else      -> R.drawable.stars_regular_0
          }
        } ?: R.drawable.stars_regular_0
      )
      businessName.text = yelpBusiness.name
      businessReview.text = yelpBusiness.review
    }

    /**
     * This interface sets the recyclerView item's actions to be handled.
     */
    interface Interaction {
      fun onItemSelected(position: Int, item: YelpBusiness)
    }
  }
}
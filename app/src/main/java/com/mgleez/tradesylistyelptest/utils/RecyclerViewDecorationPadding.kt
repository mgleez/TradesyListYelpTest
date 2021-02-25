package com.mgleez.tradesylistyelptest.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Implements RecyclerView.ItemDecoration to add a little space between the items in the
 * recycler list.
 *
 * Created by Mike Margulies 20210224
 */
class RecyclerViewDecorationPadding(
  private val padding: Int
) : RecyclerView.ItemDecoration() {
  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: RecyclerView.State
  ) {
    super.getItemOffsets(outRect, view, parent, state)
    outRect.top = padding
  }
}

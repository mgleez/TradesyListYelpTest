package com.mgleez.tradesylistyelptest.utils

import android.view.View

/**
 * View extension functions.
 *
 * Created by Mike Margulies 20210224
 */

/**
 * Shows (or hides) the view.
 * @param isVisible is an optional nullable boolean:
 * - true: View.VISIBLE (default)
 * - false: View.INVISIBLE
 * - null: View.GONE
 * @param ifNull is an optional () -> Int to handle null (default: is to return GONE)
 */
fun View.show(isVisible: Boolean? = true, ifNull: () -> Int = { View.GONE }) {
    visibility = when (isVisible) {
        true -> View.VISIBLE
        false -> View.INVISIBLE
        else -> ifNull()
    }
}

/**
 * Hides (or shows) the view.
 * @param isHidden is an optional nullable boolean:
 * - true: View.INVISIBLE (default)
 * - false: View.VISIBLE
 * - null: View.GONE
 * @param ifNull is an optional () -> Int to handle null (default: is to return GONE)
 */
fun View.hide(isHidden: Boolean? = true, ifNull: () -> Int = { View.GONE }) {
    visibility = when (isHidden) {
        true -> View.INVISIBLE
        false -> View.VISIBLE
        else -> ifNull()
    }
}

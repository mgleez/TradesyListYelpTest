package com.mgleez.tradesylistyelptest.utils

/**
 * MVI viewModel intents.
 *
 * Created by Mike Margulies 20210224
 */
sealed class ViewModelIntent<out R> {
    data class Success<out T>(val data: T) : ViewModelIntent<T>()
    data class Error(val exception: Exception) : ViewModelIntent<Nothing>()
    object Loading : ViewModelIntent<Nothing>()
}
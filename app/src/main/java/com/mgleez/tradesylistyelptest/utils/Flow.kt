package com.mgleez.tradesylistyelptest.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Encapsulates a basic request/response flow, emitting loading, success, and error states.
 *
 * Created by Mike Margulies 20210224
 */
fun <T> retrofitRoomMviFlow(
    requestDataAndInsertResponseIntoRepository: suspend () -> Unit,
    retrieveResponseDataFromRepository: suspend () -> T
): Flow<ViewModelIntent<T>> = flow {
    emit(ViewModelIntent.Loading)
    try {
        requestDataAndInsertResponseIntoRepository()
        emit(ViewModelIntent.Success(retrieveResponseDataFromRepository()))
    } catch (e: Exception) {
        emit(ViewModelIntent.Error(e))
    }
}

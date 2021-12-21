package com.behzoddev.hilttutorial.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retryWhen
import retrofit2.Response
import java.io.IOException


private val dispatcherIO : CoroutineDispatcher = Dispatchers.IO
suspend fun <T : Any> safeApiCall (
    msgError: String = "Error",
    allowRetries: Boolean = true,
    numberOfRetries: Int = 2,
    networkApiCall: suspend () -> Response<T>,
) : Flow<NetworkResult<T>> {
    var delayDuration = 1000L
    val delayFactor = 2

    return flow {

        val response = networkApiCall()
        if (response.isSuccessful) {
            response.body()?.let { result: T ->
                emit(NetworkResult.OnSuccess(result))
            }
                ?: emit(NetworkResult.OnFailure(IOException("API call successful but empty response body")))
            return@flow
        }
        @Suppress("BlockingMethodInNonBlockingContext")
        emit(
            NetworkResult.OnFailure(
                IOException("Api call failed with error - ${response.errorBody()
                    ?.string() ?: msgError
                }"
                )
            )
        )
        return@flow
    }.catch { e ->
        emit(NetworkResult.OnFailure(IOException("Exception during network API call: ${e.message}")))
        return@catch
    }.retryWhen {cause, attempt ->
        if(!allowRetries || attempt > numberOfRetries || cause !is IOException) return@retryWhen false
        delay(delayDuration)
        delayDuration *=delayFactor
        return@retryWhen true
    }.flowOn(dispatcherIO)
}

suspend fun <T: Any> getViewStateFlowForNetworkCall(IOOperation: suspend () -> Flow<NetworkResult<T>>) =
    flow {
        emit(Resource.OnLoading(true))
        IOOperation().map {
            when(it) {
                is NetworkResult.OnSuccess -> Resource.OnSuccess(it.data)
                is NetworkResult.OnFailure -> Resource.OnFailure(it.throwable)
            }
        }.collect {
            emit(it)
        }
        emit(Resource.OnLoading(false))
    }.flowOn(dispatcherIO)

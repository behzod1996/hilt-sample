package com.behzoddev.hilttutorial.repository

import com.behzoddev.hilttutorial.data.PhotoResponse
import com.behzoddev.hilttutorial.network.PhotoService
import com.behzoddev.hilttutorial.util.NetworkResult
import com.behzoddev.hilttutorial.util.safeApiCall
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotosRepositoryImpl @Inject constructor(private val retrofitClient: PhotoService) : PhotosRepository {

    @ExperimentalCoroutinesApi
    override suspend fun getPhotosByPage(page: Int): Flow<NetworkResult<PhotoResponse>> =
        safeApiCall("Found errors in API call") {
            retrofitClient.getPopularPhotos(
                page = page
            )
        }


}
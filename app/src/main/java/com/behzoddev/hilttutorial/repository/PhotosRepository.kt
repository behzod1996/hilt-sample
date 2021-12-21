package com.behzoddev.hilttutorial.repository

import com.behzoddev.hilttutorial.data.PhotoResponse
import com.behzoddev.hilttutorial.util.NetworkResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    @ExperimentalCoroutinesApi
    suspend fun getPhotosByPage(page: Int): Flow<NetworkResult<PhotoResponse>>
}
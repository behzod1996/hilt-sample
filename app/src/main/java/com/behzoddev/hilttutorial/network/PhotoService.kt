package com.behzoddev.hilttutorial.network

import com.behzoddev.hilttutorial.BuildConfig
import com.behzoddev.hilttutorial.data.PhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface PhotoService {

    @GET("/v1/photos?image_size=5,6")
    suspend fun getPopularPhotos(
        @Query("consumer_key") apiKey: String = BuildConfig.API_KEY,
        @Query ("feature")feature: String = "popular",
        @Query ("page") page: Int
    ) : Response<PhotoResponse>
}
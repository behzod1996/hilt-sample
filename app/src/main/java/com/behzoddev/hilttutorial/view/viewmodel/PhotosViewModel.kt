package com.behzoddev.hilttutorial.view.viewmodel

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.behzoddev.hilttutorial.data.Photo
import com.behzoddev.hilttutorial.data.PhotoDetails
import com.behzoddev.hilttutorial.data.PhotoResponse
import com.behzoddev.hilttutorial.repository.PhotosRepositoryImpl
import com.behzoddev.hilttutorial.util.Resource
import com.behzoddev.hilttutorial.util.getViewStateFlowForNetworkCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(private val repo: PhotosRepositoryImpl) : ViewModel() {

    val popularPhotosLiveData: MutableLiveData<Resource<List<Photo>>> by lazy {
        MutableLiveData<Resource<List<Photo>>>()
    }
    // Current page number which is displayed in screen
    private var currentPageNumber = 1
    private var maximumPageNumber = 2
    private val photoList = ArrayList<Photo>()

    private var navigatingFromDetails = false


    @ExperimentalCoroutinesApi
    fun getPhotosNextPage() {
        if (navigatingFromDetails) {
            popularPhotosLiveData.value = Resource.OnSuccess(photoList)
            return
        }

        if(currentPageNumber<maximumPageNumber) {
            viewModelScope.launch {
                getViewStateFlowForNetworkCall {
                    repo.getPhotosByPage(currentPageNumber)
                }.collect {
                    when(it) {
                        is Resource.OnLoading -> popularPhotosLiveData.value = it
                        is Resource.OnFailure -> popularPhotosLiveData.value = it
                        is Resource.OnSuccess<PhotoResponse> -> {
                            currentPageNumber++
                            maximumPageNumber = it.data.totalPages
                            photoList.addAll(it.data.photos)
                            popularPhotosLiveData.value = Resource.OnSuccess(photoList)
                        }
                    }
                }
            }
        }
    }

    fun processPhotoDetailsArgument(@NonNull args: Bundle) =
        flow {
            val photoDetails = args.getParcelable<PhotoDetails>("photoDetails")

            photoDetails?.let {
                emit(Resource. OnSuccess(it))
            } ?: run {
                emit(Resource.OnFailure(Exception("No Photo DetailsFound")))
        }
    }.asLiveData()

    @ExperimentalCoroutinesApi
    fun onRecyclerViewScrolledToBottom() {
        if(navigatingFromDetails) navigatingFromDetails = false
        getPhotosNextPage()
    }

}
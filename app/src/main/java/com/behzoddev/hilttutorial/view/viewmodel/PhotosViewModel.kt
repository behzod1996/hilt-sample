package com.behzoddev.hilttutorial.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.behzoddev.hilttutorial.data.Photo
import com.behzoddev.hilttutorial.repository.PhotosRepositoryImpl
import com.behzoddev.hilttutorial.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class PhotosViewModel constructor(private val repo: PhotosRepositoryImpl) : ViewModel() {

    val popularPhotosLiveData: MutableLiveData<Resource<List<Photo>>> by lazy {
        MutableLiveData<Resource<List<Photo>>>()
    }
    // Current page number which is displayed in screen
    private var currentPageNumber = 1
    private var maximumPageNumber = 2
    private val photoList = ArrayList<Photo>()

}
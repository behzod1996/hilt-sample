package com.behzoddev.hilttutorial.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.behzoddev.hilttutorial.common.loadImage
import com.behzoddev.hilttutorial.common.loadUrl
import com.behzoddev.hilttutorial.common.toastLong
import com.behzoddev.hilttutorial.common.toastShort
import com.behzoddev.hilttutorial.data.Photo
import com.behzoddev.hilttutorial.data.PhotoDetails
import com.behzoddev.hilttutorial.databinding.FragmentDetailBinding
import com.behzoddev.hilttutorial.util.Resource
import com.behzoddev.hilttutorial.view.base.BaseFragment
import com.behzoddev.hilttutorial.view.viewmodel.PhotosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val viewModel : PhotosViewModel by activityViewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding =
        FragmentDetailBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            viewModel.processPhotoDetailsArgument(it)
                .observe(viewLifecycleOwner, Observer { result ->
                    when(result) {
                        is Resource.OnSuccess -> bind(result.data)
                        is Resource.OnFailure -> context?.toastLong(result.throwable.message!!)
                    }
                })
        } ?: context?.toastLong("Invalid data")
    }


    private fun bind(photoDetails: PhotoDetails) = with(binding) {
        ivPhotoDetails.loadImage(photoDetails.url)
        tvLikesValue.text = photoDetails.votesCount.toString()
        tvCommentsValue.text = photoDetails.comments.toString()
        tvDescriptionValue.text = photoDetails.description.toString()
        tvPostedValue.text = photoDetails.durationPosted.toString()
    }


}
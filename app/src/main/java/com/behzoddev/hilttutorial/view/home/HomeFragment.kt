package com.behzoddev.hilttutorial.view.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.behzoddev.hilttutorial.R
import com.behzoddev.hilttutorial.common.toastShort
import com.behzoddev.hilttutorial.data.Photo
import com.behzoddev.hilttutorial.data.PhotoDetails
import com.behzoddev.hilttutorial.databinding.FragmentHomeBinding
import com.behzoddev.hilttutorial.util.Resource
import com.behzoddev.hilttutorial.util.durationPosted
import com.behzoddev.hilttutorial.util.getValidation
import com.behzoddev.hilttutorial.view.adapter.ListItemClickListener
import com.behzoddev.hilttutorial.view.adapter.PhotosAdapter
import com.behzoddev.hilttutorial.view.base.BaseFragment
import com.behzoddev.hilttutorial.view.viewmodel.PhotosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(){

    private val viewModel: PhotosViewModel by activityViewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    private val photosItemClickListener: ListItemClickListener<Photo> = {
        val photoDetails = PhotoDetails(
            it.images[0].httpsUrl,
            it.description,
            it.votesCount,
            it.commentsCount,
            it.rating,
            it.timesViewed,
            it.name,
            it.user?.fullname,
            it.user?.avatars?.default?.https,
            it.getValidation(),
            it.durationPosted()
        )
        val bundle = bundleOf("photoDetails" to photoDetails)
        findNavController().navigate(R.id.homeToDetail,bundle)
    }
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater,container,false)

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(!recyclerView.canScrollVertically(1)){
                    viewModel.onRecyclerViewScrolledToBottom()
                }
            }


        })

        viewModel.popularPhotosLiveData.observe(viewLifecycleOwner, Observer { result ->
            when(result) {
                is Resource.OnLoading -> binding.progressBar.visibility =
                    if(result.isLoading) View.VISIBLE else View.GONE
                is Resource.OnFailure -> result.throwable.message ?.let {
                    context?.toastShort(it)
                }
                is Resource.OnSuccess -> {
                    if(binding.rvList.adapter == null) {
                        binding.rvList.adapter = PhotosAdapter(
                            photosItemClickListener
                        )
                    }
                    (binding.rvList.adapter as PhotosAdapter).differ.submitList(result.data)
                }
            }
        })
        viewModel.getPhotosNextPage()
    }

}
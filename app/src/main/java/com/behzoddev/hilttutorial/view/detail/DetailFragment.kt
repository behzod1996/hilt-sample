package com.behzoddev.hilttutorial.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.behzoddev.hilttutorial.databinding.FragmentDetailBinding
import com.behzoddev.hilttutorial.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater,container,false)
    }

}
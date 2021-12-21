package com.behzoddev.hilttutorial.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.behzoddev.hilttutorial.databinding.FragmentHomeBinding
import com.behzoddev.hilttutorial.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(){

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater,container,false)

}
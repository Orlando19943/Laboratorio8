package com.example.laboratorio8.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.example.laboratorio8.databinding.NewsFragmentBinding

class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: NewsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewsFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner { this.lifecycle }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        binding.viewModel = viewModel
    }

}

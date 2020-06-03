package com.example.laboratorio8.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs


import com.example.laboratorio8.databinding.NewsFragmentBinding

class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: NewsFragmentBinding
    private lateinit var viewModelFactory: NewsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewsFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this

        val args by navArgs<NewsFragmentArgs>()
        viewModelFactory = NewsViewModelFactory(args.keyWord,args.points,args.author)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)
        binding.viewModel = viewModel

        binding.newsList.adapter = HackerNewsAdapter(HackerNewsAdapter.OnClickListener {
            viewModel.openRepoUrl(it)
        })



    }

    private fun openHackerNewsPage(url: String) {
        val page: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, page)
        val packageManager = activity?.packageManager
        if (packageManager?.let { intent.resolveActivity(it) } != null) {
            startActivity(intent)
        }
    }

}

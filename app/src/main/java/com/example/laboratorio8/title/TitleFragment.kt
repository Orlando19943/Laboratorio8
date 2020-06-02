package com.example.laboratorio8.title


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import com.example.laboratorio8.R
import com.example.laboratorio8.databinding.TitleFragmentBinding

class TitleFragment : Fragment() {


    private lateinit var viewModel: TitleViewModel

    private lateinit var binding: TitleFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TitleFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(TitleViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.viewNews.observe(viewLifecycleOwner, Observer {
            requireView().findNavController().navigate(R.id.action_titleFragment_to_newsFragment)
            viewModel.viewNewsComplete()
        })
        viewModel.viewNews.observe(viewLifecycleOwner, Observer {
            binding.executePendingBindings()
        })
    }

}

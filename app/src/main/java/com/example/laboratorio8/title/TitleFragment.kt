package com.example.laboratorio8.title


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import com.example.laboratorio8.R
import com.example.laboratorio8.databinding.TitleFragmentBinding
import com.example.laboratorio8.network.NewsApiStatus

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

        binding.button.setOnClickListener {
            viewModel.actionViewNews()
        }

        viewModel.viewNews.observe(viewLifecycleOwner, Observer {
            if (it) {
                println("Prueba: " + viewModel.keyWord.value)
                requireView().findNavController().navigate(
                    TitleFragmentDirections.actionTitleFragmentToNewsFragment(binding.keyWordText.text.toString(),
                        viewModel.points.value ?: "",viewModel.author.value ?: ""))
                viewModel.viewNewsComplete()
            }
        })
        viewModel.viewNews.observe(viewLifecycleOwner, Observer {
            binding.executePendingBindings()
        })
        viewModel.status.observe(viewLifecycleOwner, Observer {
            if (it == NewsApiStatus.ERROR){
                Toast.makeText(this.context, "Fallo al conectarse al internet o no se enconrto ningun resultado", Toast.LENGTH_SHORT).show()
                viewModel.startStatus()
            }
        })
    }

}

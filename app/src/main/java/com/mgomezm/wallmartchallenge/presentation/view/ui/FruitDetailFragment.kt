package com.mgomezm.wallmartchallenge.presentation.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mgomezm.wallmartchallenge.databinding.FragmentFruitDetailsBinding
import com.mgomezm.wallmartchallenge.presentation.view.state.State
import com.mgomezm.wallmartchallenge.presentation.viewmodel.FruitViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FruitDetailFragment : Fragment() {

    private lateinit var binding: FragmentFruitDetailsBinding
    private val args: FruitDetailFragmentArgs by navArgs()
    private val viewModel by viewModels<FruitViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFruitDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.queryFruit(args.id)
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.fruit.observe(viewLifecycleOwner) { response ->
            when (response) {
                is State.Loading -> {
                    binding.fruitDetailsFetchProgress.visibility = View.VISIBLE
                    binding.fruitDetailsNotFound.visibility = View.GONE
                }
                is State.Success -> {
                    if (response.value?.data?.fruit == null) {
                        binding.fruitDetailsFetchProgress.visibility = View.GONE
                        binding.fruitDetailsNotFound.visibility = View.VISIBLE
                    } else {
                        binding.query = response.value.data
                        binding.fruitDetailsFetchProgress.visibility = View.GONE
                        binding.fruitDetailsNotFound.visibility = View.GONE
                    }
                }
                is State.Error -> {
                    binding.fruitDetailsFetchProgress.visibility = View.GONE
                    binding.fruitDetailsNotFound.visibility = View.VISIBLE
                }
            }
        }
    }

}
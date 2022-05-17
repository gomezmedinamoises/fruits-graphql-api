package com.mgomezm.wallmartchallenge.presentation.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mgomezm.wallmartchallenge.databinding.FragmentFruitsListBinding
import com.mgomezm.wallmartchallenge.presentation.view.adapter.FruitAdapter
import com.mgomezm.wallmartchallenge.presentation.view.state.State
import com.mgomezm.wallmartchallenge.presentation.viewmodel.FruitViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FruitsListFragment : Fragment() {

    private lateinit var binding: FragmentFruitsListBinding
    private val fruitAdapter by lazy { FruitAdapter() }
    private val viewModel by viewModels<FruitViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFruitsListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fruitsRecyclerView.adapter = fruitAdapter
        viewModel.queryFruitsList()
        observeLiveData()

        // Call onItemClicked in order to go to the fruit detail
        fruitAdapter.onItemClicked = { fruit ->
            fruit.let {
                if (!fruit.id.isNullOrBlank()) {
                    findNavController().navigate(
                        FruitsListFragmentDirections.actionFruitsListFragmentToFruitDetailFragment(id = fruit.id)
                    )
                }
            }
        }
    }

    private fun observeLiveData() {
        viewModel.fruitsList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is State.Loading -> {
                    binding.fruitsRecyclerView.visibility = View.GONE
                    binding.fruitsFetchProgress.visibility = View.VISIBLE
                }
                is State.Success -> {
                    if (response.value?.data?.fruits?.size == 0) {
                        fruitAdapter.submitList(emptyList())
                        binding.fruitsFetchProgress.visibility = View.GONE
                        binding.fruitsRecyclerView.visibility = View.GONE
                        binding.fruitsEmptyText.visibility = View.VISIBLE
                    } else {
                        binding.fruitsRecyclerView.visibility = View.VISIBLE
                        binding.fruitsEmptyText.visibility = View.GONE
                    }
                    val results = response.value?.data?.fruits
                    fruitAdapter.submitList(results)
                    binding.fruitsFetchProgress.visibility = View.GONE
                }
                is State.Error -> {
                    fruitAdapter.submitList(emptyList())
                    binding.fruitsFetchProgress.visibility = View.GONE
                    binding.fruitsRecyclerView.visibility = View.GONE
                    binding.fruitsEmptyText.visibility = View.VISIBLE
                }
            }
        }
    }
}
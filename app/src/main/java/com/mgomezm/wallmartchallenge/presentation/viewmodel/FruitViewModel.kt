package com.mgomezm.wallmartchallenge.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.annotation.ExperimentalCoilApi
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.mgomezm.wallmartchallenge.FruitQuery
import com.mgomezm.wallmartchallenge.FruitsListQuery
import com.mgomezm.wallmartchallenge.data.repository.FruitRepository
import com.mgomezm.wallmartchallenge.presentation.view.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoilApi
@HiltViewModel
class FruitViewModel @Inject constructor(
    private val repository: FruitRepository
) : ViewModel() {

    // Public and private variables for fetching a fruit list
    private val _fruitsList by lazy {
        MutableLiveData<State<Response<FruitsListQuery.Data>>>()
    }
    val fruitsList: LiveData<State<Response<FruitsListQuery.Data>>>
        get() = _fruitsList

    // Public and private  variables for fetching a fruit
    private val _fruit by lazy {
        MutableLiveData<State<Response<FruitQuery.Data>>>()
    }
    val fruit: LiveData<State<Response<FruitQuery.Data>>>
        get() = _fruit

    // Function to obtain a fruits list
    fun queryFruitsList() = viewModelScope.launch {
        _fruitsList.postValue(State.Loading())
        try {
            val response = repository.queryFruitsList()
            _fruitsList.postValue(State.Success(response))
        } catch (ae: ApolloException) {
            Log.d("Apollo Exception", "Failure", ae)
            _fruitsList.postValue(State.Error("Error fetching fruits"))
        }
    }

    // Function to obtain a fruit
    fun queryFruit(id: String) = viewModelScope.launch {
        _fruit.postValue(State.Loading())
        try {
            val response = repository.queryFruit(id)
            _fruit.postValue(State.Success(response))
        } catch (ae: ApolloException) {
            Log.d("ApolloException", "Failure", ae)
            _fruit.postValue(State.Error("Error fetching fruits"))
        }
    }

}
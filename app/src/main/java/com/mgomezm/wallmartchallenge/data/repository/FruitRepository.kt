package com.mgomezm.wallmartchallenge.data.repository

import com.apollographql.apollo.api.Response
import com.mgomezm.wallmartchallenge.FruitQuery
import com.mgomezm.wallmartchallenge.FruitsListQuery

interface FruitRepository {

    suspend fun queryFruitsList() : Response<FruitsListQuery.Data>

    suspend fun queryFruit(id: String) : Response<FruitQuery.Data>
}
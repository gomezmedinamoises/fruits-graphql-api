package com.mgomezm.wallmartchallenge.data.repository

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.mgomezm.wallmartchallenge.FruitQuery
import com.mgomezm.wallmartchallenge.FruitsListQuery
import com.mgomezm.wallmartchallenge.data.networking.FruitsApi
import javax.inject.Inject

class FruitRepositoryImpl @Inject constructor(
    private val webService: FruitsApi
): FruitRepository {

    override suspend fun queryFruitsList(): Response<FruitsListQuery.Data> {
        return webService.getApolloClient().query(FruitsListQuery()).await()
    }

    override suspend fun queryFruit(id: String): Response<FruitQuery.Data> {
        return webService.getApolloClient().query(FruitQuery(id)).await()
    }
}
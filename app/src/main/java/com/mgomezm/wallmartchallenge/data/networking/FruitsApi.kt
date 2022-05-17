package com.mgomezm.wallmartchallenge.data.networking

import android.os.Looper
import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient

class FruitsApi {

    fun getApolloClient(): ApolloClient {
        check(Looper.myLooper() == Looper.getMainLooper()) {
            "Only the main thread can get the apolloClient instance"
        }

        val okHttpClient = OkHttpClient.Builder().build()
        return ApolloClient.builder()
            .serverUrl("https://fruits-api.netlify.app/graphql/")
            .okHttpClient(okHttpClient)
            .build()
    }
}
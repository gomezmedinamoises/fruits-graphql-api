package com.mgomezm.wallmartchallenge.di

import com.mgomezm.wallmartchallenge.data.networking.FruitsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideWebService() = FruitsApi()
}
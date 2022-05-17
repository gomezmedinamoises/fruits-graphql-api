package com.mgomezm.wallmartchallenge.di

import com.mgomezm.wallmartchallenge.data.repository.FruitRepository
import com.mgomezm.wallmartchallenge.data.repository.FruitRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRepository(repo: FruitRepositoryImpl): FruitRepository
}
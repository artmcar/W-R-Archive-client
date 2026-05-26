package com.artmcar.wrarchive.di

import com.artmcar.wrarchive.domain.repository.WarrantyRepository
import com.artmcar.wrarchive.domain.repository.WarrantyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWarrantyRepository(
        impl: WarrantyRepositoryImpl
    ): WarrantyRepository
}
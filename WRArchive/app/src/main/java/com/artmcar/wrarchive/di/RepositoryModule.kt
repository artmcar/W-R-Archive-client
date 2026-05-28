package com.artmcar.wrarchive.di

import com.artmcar.wrarchive.data.repository.AuthRepositoryImpl
import com.artmcar.wrarchive.domain.repository.ReceiptRepository
import com.artmcar.wrarchive.data.repository.ReceiptRepositoryImpl
import com.artmcar.wrarchive.data.repository.SettingsRepositoryImpl
import com.artmcar.wrarchive.data.repository.SyncRepositoryImpl
import com.artmcar.wrarchive.domain.repository.WarrantyRepository
import com.artmcar.wrarchive.data.repository.WarrantyRepositoryImpl
import com.artmcar.wrarchive.domain.repository.AuthRepository
import com.artmcar.wrarchive.domain.repository.SettingsRepository
import com.artmcar.wrarchive.domain.repository.SyncRepository
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
    abstract fun bindWarrantyRepository(impl: WarrantyRepositoryImpl): WarrantyRepository

    @Binds
    @Singleton
    abstract fun bindReceiptRepository(impl: ReceiptRepositoryImpl): ReceiptRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindSyncRepository(
        impl:   SyncRepositoryImpl
    ): SyncRepository

}
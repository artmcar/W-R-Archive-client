package com.artmcar.wrarchive.di

import android.content.Context
import androidx.room.Room
import com.artmcar.wrarchive.data.local.room.warranty.WarrantyDao
import com.artmcar.wrarchive.data.local.room.AppDatabase
import com.artmcar.wrarchive.data.local.room.receipt.ReceiptDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "warranty_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWarrantyDao(
        database: AppDatabase
    ): WarrantyDao {

        return database.warrantyDao()
    }

    @Provides
    @Singleton
    fun provideReceiptDao(
        database: AppDatabase
    ): ReceiptDao {
        return database.receiptDao()
    }
}
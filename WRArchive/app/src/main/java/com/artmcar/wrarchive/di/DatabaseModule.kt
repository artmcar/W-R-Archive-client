package com.artmcar.wrarchive.di

import android.content.Context
import androidx.room.Room
import com.artmcar.wrarchive.data.local.room.WarrantyDao
import com.artmcar.wrarchive.data.local.room.WarrantyDatabase
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
    ): WarrantyDatabase {

        return Room.databaseBuilder(
            context,
            WarrantyDatabase::class.java,
            "warranty_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWarrantyDao(
        database: WarrantyDatabase
    ): WarrantyDao {

        return database.warrantyDao()
    }
}
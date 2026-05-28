package com.artmcar.wrarchive.domain.repository

interface SyncRepository {
    suspend fun syncPendingData()
    suspend fun downloadRemoteData()
}
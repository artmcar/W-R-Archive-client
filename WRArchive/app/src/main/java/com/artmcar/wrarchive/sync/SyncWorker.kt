package com.artmcar.wrarchive.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.artmcar.wrarchive.domain.repository.SyncRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: SyncRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork():
            Result {
        return try {
            repository.syncPendingData()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
package com.artmcar.wrarchive.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.artmcar.wrarchive.domain.repository.SyncRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(

    @Assisted
    appContext: Context,

    @Assisted
    workerParams: WorkerParameters,
    private val syncRepository: SyncRepository
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {

        Log.d(
            "SYNC_WORKER",
            "WORK STARTED"
        )
        return try {
            syncRepository.syncPendingData()
            Log.d(
                "SYNC_WORKER",
                "SYNC FINISHED"
            )
            syncRepository.downloadRemoteData()
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}

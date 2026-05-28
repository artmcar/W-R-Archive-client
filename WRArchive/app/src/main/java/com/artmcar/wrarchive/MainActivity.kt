package com.artmcar.wrarchive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.artmcar.wrarchive.presentation.navigation.AppNavigation
import com.artmcar.wrarchive.presentation.theme.ThemeViewModel
import com.artmcar.wrarchive.sync.SyncWorker
import com.artmcar.wrarchive.ui.theme.WRArchiveTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startSyncWorker()
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val uiState by themeViewModel.uiState.collectAsState()
            WRArchiveTheme(
                darkTheme = uiState.isDarkTheme
            ) {
                AppNavigation()
            }
        }
    }
    private fun startSyncWorker() {
        val request =
            PeriodicWorkRequestBuilder<SyncWorker>(
                15,
                TimeUnit.MINUTES
            ).build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "sync_worker",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
    }
}
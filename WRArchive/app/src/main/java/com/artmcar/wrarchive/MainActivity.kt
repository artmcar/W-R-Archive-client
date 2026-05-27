package com.artmcar.wrarchive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.artmcar.wrarchive.presentation.navigation.AppNavigation
import com.artmcar.wrarchive.presentation.theme.ThemeViewModel
import com.artmcar.wrarchive.ui.theme.WRArchiveTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}
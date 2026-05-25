package com.artmcar.wrarchive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.artmcar.wrarchive.ui.theme.WRArchiveTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WRArchiveTheme {
                AppNavigation()
            }
        }
    }
}
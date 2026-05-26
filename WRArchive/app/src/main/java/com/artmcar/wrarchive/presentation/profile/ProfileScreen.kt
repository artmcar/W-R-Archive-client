package com.artmcar.wrarchive.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.artmcar.wrarchive.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    onBack: () -> Unit,
    viewModel: ProfileViewModel =
        hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(R.string.profile))
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {
            ListItem(
                headlineContent = {
                    Text(stringResource(R.string.dark_theme))
                },
                trailingContent = {
                    Switch(
                        checked =
                            uiState.darkTheme,

                        onCheckedChange = {

                            viewModel.updateDarkTheme(it)
                        }
                    )
                }
            )
            ListItem(
                headlineContent = {
                    Text(stringResource(R.string.cloud_sync))
                },
                trailingContent = {
                    Switch(
                        checked =
                            uiState.cloudSync,

                        onCheckedChange = {

                            viewModel.updateCloudSync(it)
                        }
                    )
                }
            )
            ListItem(
                headlineContent = {
                    Text(stringResource(R.string.remember_login))
                },
                trailingContent = {
                    Switch(
                        checked =
                            uiState.rememberLogin,

                        onCheckedChange = {

                            viewModel.updateRememberLogin(it)
                        }
                    )
                }
            )
            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.logout))
            }
        }
    }
}
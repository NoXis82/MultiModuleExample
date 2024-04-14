package com.example.navigationtestapp

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {
    val uiState by viewModel.uiStateFlow.collectAsState()
    val scope = rememberCoroutineScope() // Create a coroutine scope
    var eventCheck by remember {
        mutableStateOf("not event")
    }
    viewModel.CollectEventEffect { event ->
        return@CollectEventEffect when (event) {
            Event.StartForgotPasswordFeature -> {
                eventCheck = "StartForgotPasswordFeature"
            }

            Event.UserFeature -> {
                eventCheck = "StartUserFeature"
            }
        }
    }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        AppTopBar(
//            modifier = Modifier.fillMaxWidth(),
//            state = toolbarUiState,
//        )
        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = "Home screen"
        )
        Spacer(modifier = Modifier.size(6.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }


        Text(text = if (uiState.items.isEmpty()) "Ждем" else "${uiState.items}")

        Button(onClick = {
            viewModel.onForgotPasswordClick()
        }) {
            Text(text = "Go To Forgot Password Flow")
        }
        Button(onClick = {
            viewModel.onUserClick()
        }) {
            Text(text = "Go To User Flow")
        }
        Text(text = "This event check: ${eventCheck}")
    }

}



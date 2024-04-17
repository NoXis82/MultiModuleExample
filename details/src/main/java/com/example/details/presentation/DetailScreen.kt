package com.example.details.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.details.domain.model.ProductItem
import com.example.details.event.DetailUIEvent
import com.example.details.viewmodel.DetailViewModel

@Composable
fun DetailScreen(productItem: ProductItem?) {

    val viewModel: DetailViewModel = hiltViewModel()
    val stateUi by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column {
            productItem?.apply {
                Text(text = subText)
                Text(text = text)
                Text(text = productId)
                Text(text = review)
                Text(text = questions)
                Text(text = rating)
            }

        }
    }

    BackHandler(enabled = true) {
        viewModel.onEvent(DetailUIEvent.Dismiss)
    }
}
package com.example.navigationtestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.common_core.util.Until.fromJson
import com.example.details.domain.model.ProductItem
import com.example.details.presentation.DetailScreen
import com.example.navigation.AppNavigation
import com.example.navigation.Navigator
import com.example.product.presentation.screen.ProductListScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var title by remember { mutableStateOf("Home") }
            var visibleBack by remember { mutableStateOf(false) }
            val
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(text = title)
                        },
                        navigationIcon = {
                            if (visibleBack) {
                                IconButton(onClick = { navigator.goBack() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        },
                        actions = {

                        },
                        scrollBehavior = scrollBehavior
                    )
                },
                content = { paddingValues ->
                    AppNavigation(
                        navigator = navigator,
                        detailScreen = {
                            val item = it.fromJson(ProductItem::class.java)
                            title = "Detail Screen"
                            visibleBack = true
                            DetailScreen(item)

                        },
                        listScreen = {
                            title = "List Screen"
                            visibleBack = false
                            ProductListScreen(paddingValues)
                        }
                    )
                }
            )
        }
    }
}
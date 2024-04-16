package com.example.navigationtestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.common_core.util.Until.fromJson
import com.example.navigation.AppNavigation
import com.example.navigation.Navigator
import com.example.product.domain.models.Product
import com.example.product.presentation.screen.ProductListScreen
import com.example.product.presentation.screen.Test
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation(
                navigator = navigator,
                detailScreen = {
                    //val item = it.fromJson(Product::class.java)
                    Test(it)
                },
                listScreen = { ProductListScreen() }
            )
        }
    }
}
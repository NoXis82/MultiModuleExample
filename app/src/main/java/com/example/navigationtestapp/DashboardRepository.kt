package com.example.navigationtestapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.UUID

class DashboardRepository {

    suspend fun getHomeItems(): List<Dashboard> = withContext(Dispatchers.IO) {

        delay(4500)

        buildList {
            repeat(1) {
                add(
                    Dashboard(
                        id = UUID.randomUUID().toString(),
                        title = UUID.randomUUID().toString(),
                        description = UUID.randomUUID().toString(),
                    )
                )
            }
        }
    }
}
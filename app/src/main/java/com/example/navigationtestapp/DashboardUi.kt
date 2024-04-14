package com.example.navigationtestapp

data class DashboardUi(
    val title: String,
    val description: String,
)

fun Dashboard.toDashboardUi() = DashboardUi(
    title = this.title,
    description = this.description,
)

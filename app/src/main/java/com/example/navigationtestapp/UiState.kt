package com.example.navigationtestapp

data class UiState(
    val isLoading: Boolean = false,
    val items: List<DashboardUi> = emptyList(),
    val filter: String = "",
)
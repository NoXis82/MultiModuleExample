package com.example.product.presentation.event

sealed interface ListUIEvent {

    data object Dismiss : ListUIEvent

    data object GetList: ListUIEvent
}
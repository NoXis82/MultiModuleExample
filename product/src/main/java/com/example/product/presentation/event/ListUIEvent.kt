package com.example.product.presentation.event

import com.example.product.domain.models.Product

sealed class ListUIEvent {

    data object Dismiss : ListUIEvent()

    data object GetList: ListUIEvent()

    data class ProductClicked(val item: Product) : ListUIEvent()
}
package com.example.details.event


sealed class DetailUIEvent {

    data object Dismiss : DetailUIEvent()

//    data object GetList: DetailUIEvent()

//    data class ProductClicked(val item: Product) : DetailUIEvent()
}
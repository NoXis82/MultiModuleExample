package com.example.navigationtestapp

sealed interface Event {

   data object StartForgotPasswordFeature : Event
   data object UserFeature : Event
}
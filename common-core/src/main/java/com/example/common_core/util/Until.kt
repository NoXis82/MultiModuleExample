package com.example.common_core.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object Until {

    fun <A> String.fromJson(type: Class<A>): A {
        val gson = GsonBuilder().create()
        return gson.fromJson(this, type)
    }

    fun <A> A.toJson(): String? {
        val gson = GsonBuilder().create()
        return gson.toJson(this)
    }
}
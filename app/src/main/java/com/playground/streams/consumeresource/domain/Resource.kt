package com.playground.streams.consumeresource.domain

sealed class Resource<T> {

    data class Loading<T>(private val nothing: Nothing? = null) : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Failure<T>(val throwable: Throwable) : Resource<T>()
}

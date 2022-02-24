package com.playground.streams.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Timer @Inject constructor() {

    operator fun invoke(): Flow<Int> = flow {
        repeat(10) {
            emit(it)
            delay(1000)
        }
    }
}
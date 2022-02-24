package com.playground.streams.subjects

import com.playground.streams.util.Timer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DataSourceWithSharedFlow @Inject constructor(private val timer: Timer) {

    val publishFlow = MutableSharedFlow<Int>(extraBufferCapacity = 1)
    val behaviorFlow = MutableSharedFlow<Int>(replay = 1)
    val replayFlow = MutableSharedFlow<Int>(replay = Int.MAX_VALUE)

    fun startEmission(): Flow<Int> {
        return timer()
            .onEach { number ->
                publishFlow.tryEmit(number)
                behaviorFlow.tryEmit(number)
                replayFlow.tryEmit(number)
            }
    }
}

package com.playground.streams.subjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedFlowViewModel @Inject constructor(
    private val dataSourceWithSharedFlow: DataSourceWithSharedFlow
) : ViewModel() {

    private val _time = Channel<Int>(Channel.BUFFERED)
    val time: Flow<Int> = _time.receiveAsFlow()

    private val _behaviorType = Channel<BehaviorType>(Channel.BUFFERED)
    val behaviorType: Flow<BehaviorType> = _behaviorType.receiveAsFlow()

    init {
        dataSourceWithSharedFlow.startEmission()
            .onEach { number -> _time.send(number) }
            .launchIn(viewModelScope)
    }

    fun startObserving() = viewModelScope.launch {
        launch {
            dataSourceWithSharedFlow.publishFlow.collect {
                _behaviorType.send(BehaviorType.Publish(it))
            }
        }
        launch {
            dataSourceWithSharedFlow.behaviorFlow.collect {
                _behaviorType.send(BehaviorType.Behavior(it))
            }
        }
        launch {
            dataSourceWithSharedFlow.replayFlow.collect {
                _behaviorType.send(BehaviorType.Replay(it))
            }
        }
    }
}

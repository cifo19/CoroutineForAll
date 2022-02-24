package com.playground.streams.subjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SubjectsViewModel @Inject constructor(
    private val dataSourceWithSubjects: DataSourceWithSubjects
) : ViewModel() {

    private val _time = Channel<Int>(Channel.BUFFERED)
    val time: Flow<Int> = _time.receiveAsFlow()

    private val _behaviorType = Channel<BehaviorType>(Channel.BUFFERED)
    val behaviorType: Flow<BehaviorType> = _behaviorType.receiveAsFlow()

    init {
        dataSourceWithSubjects.startEmission()
            .onEach { number -> _time.send(number) }
            .launchIn(viewModelScope)
    }

    fun startObserving() {
        dataSourceWithSubjects.publishSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _behaviorType.trySend(BehaviorType.Publish(it)) }

        dataSourceWithSubjects.behaviorSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _behaviorType.trySend(BehaviorType.Behavior(it)) }

        dataSourceWithSubjects.replaySubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _behaviorType.trySend(BehaviorType.Replay(it)) }
    }
}
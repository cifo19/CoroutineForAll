package com.playground.streams.subjects

import com.playground.streams.util.Timer
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DataSourceWithSubjects @Inject constructor(private val timer: Timer) {

    val publishSubject: PublishSubject<Int> = PublishSubject.create()
    val behaviorSubject: BehaviorSubject<Int> = BehaviorSubject.create()
    val replaySubject: ReplaySubject<Int> = ReplaySubject.create()

    fun startEmission(): Flow<Int> {
        return timer()
            .onEach { number ->
                publishSubject.onNext(number)
                behaviorSubject.onNext(number)
                replaySubject.onNext(number)
            }
    }
}

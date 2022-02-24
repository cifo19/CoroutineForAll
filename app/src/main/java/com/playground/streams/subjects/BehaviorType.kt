package com.playground.streams.subjects

sealed class BehaviorType(open val value: Int) {
    data class Publish(override val value: Int) : BehaviorType(value)
    data class Behavior(override val value: Int) : BehaviorType(value)
    data class Replay(override val value: Int) : BehaviorType(value)
}

package com.playground.streams.consumeresource.presentation.data

import com.playground.streams.consumeresource.domain.model.ContentEntity

sealed class ConsumeResourceUIState {
    object Loading: ConsumeResourceUIState()
    data class Content(val contentEntity: ContentEntity): ConsumeResourceUIState()
    data class Failure(val throwable: Throwable): ConsumeResourceUIState()
}

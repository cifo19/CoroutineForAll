package com.playground.streams.consumeresource.data

import com.playground.streams.consumeresource.data.model.ContentResponse
import com.playground.streams.consumeresource.domain.model.ContentEntity
import javax.inject.Inject

class ContentEntityMapper @Inject constructor() {

    fun map(contentResponse: ContentResponse): ContentEntity {
        return ContentEntity(
            title = contentResponse.title,
            description = contentResponse.description
        )
    }
}

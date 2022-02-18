package com.playground.streams.consumeresource.domain

import com.playground.streams.consumeresource.domain.model.ContentEntity
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun fetchDetail(id: String): Observable<Resource<ContentEntity>>

    fun fetchDetailFlow(id: String): Flow<Resource<ContentEntity>>
}

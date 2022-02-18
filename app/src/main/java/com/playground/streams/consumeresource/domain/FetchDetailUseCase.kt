package com.playground.streams.consumeresource.domain

import com.playground.streams.consumeresource.domain.model.ContentEntity
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchDetailUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    fun fetchDetail(id: String): Observable<Resource<ContentEntity>> {
        return productRepository.fetchDetail(id)
    }

    fun fetchDetailFlow(id: String): Flow<Resource<ContentEntity>> {
        return productRepository.fetchDetailFlow(id)
    }
}

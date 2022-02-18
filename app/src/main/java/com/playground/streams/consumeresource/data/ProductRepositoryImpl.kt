package com.playground.streams.consumeresource.data

import com.playground.streams.consumeresource.domain.ProductRepository
import com.playground.streams.consumeresource.domain.Resource
import com.playground.streams.consumeresource.domain.model.ContentEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource,
    private val contentEntityMapper: ContentEntityMapper
) : ProductRepository {

    override fun fetchDetail(id: String): Observable<Resource<ContentEntity>> {
        return productDataSource.fetchDetail(id)
            .toObservable()
            .observeOn(Schedulers.computation())
            .map { contentEntityMapper.map(it) }
            .map<Resource<ContentEntity>> { Resource.Success(it) }
            .onErrorReturn { Resource.Failure(it) }
            .startWithItem(Resource.Loading())
    }

    // region suspend
    override fun fetchDetailFlow(id: String): Flow<Resource<ContentEntity>> {
        return flow { emit(productDataSource.fetchDetailSuspend(id)) }
            .map { contentEntityMapper.map(it) }
            .map<ContentEntity, Resource<ContentEntity>> { Resource.Success(it) }
            .flowOn(Dispatchers.Default)
            .catch { emit(Resource.Failure(it)) }
            .onStart { emit(Resource.Loading()) }
    }
    // endregion
}

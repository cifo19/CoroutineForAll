package com.playground.streams.consumeresource.data

import com.playground.streams.consumeresource.data.model.ContentResponse
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProductDataSource @Inject constructor() {

    fun fetchDetail(id: String): Single<ContentResponse> {
        return Single.create<ContentResponse?> {
            val response = getContentResponse(id)
            it.onSuccess(response)
        }
            .delay(2, TimeUnit.SECONDS)
    }

    // region suspend
    suspend fun fetchDetailSuspend(id: String): ContentResponse {
        delay(2000)
        return getContentResponse(id)
    }
    // endregion

    private fun getContentResponse(id: String): ContentResponse {
        return ContentResponse("Hello $id", "Hello Description", 10.0)
    }
}

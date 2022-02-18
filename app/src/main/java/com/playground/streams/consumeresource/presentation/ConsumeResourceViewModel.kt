package com.playground.streams.consumeresource.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playground.streams.consumeresource.domain.FetchDetailUseCase
import com.playground.streams.consumeresource.domain.Resource
import com.playground.streams.consumeresource.domain.model.ContentEntity
import com.playground.streams.consumeresource.presentation.data.ConsumeResourceUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ConsumeResourceViewModel @Inject constructor(
    private val fetchDetailUseCase: FetchDetailUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _uiState = MutableStateFlow<ConsumeResourceUIState>(ConsumeResourceUIState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getDetail(id: String) {
        compositeDisposable += fetchDetailUseCase.fetchDetail(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { onResource(it) },
                {
                    // Log
                }
            )
    }

    fun getDetailAsFlow(id: String) {
        fetchDetailUseCase.fetchDetailFlow(id)
            .onEach { onResource(it) }
            .catch {
                // Log
            }
            .launchIn(viewModelScope)
    }

    private fun onResource(resource: Resource<ContentEntity>) {
        _uiState.value = when (resource) {
            is Resource.Failure -> {
                ConsumeResourceUIState.Failure(resource.throwable)
                // Log
            }
            is Resource.Loading -> {
                ConsumeResourceUIState.Loading
            }
            is Resource.Success -> {
                ConsumeResourceUIState.Content(resource.data)
            }
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
package com.playground.streams.sharedevent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playground.streams.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    // region sharedFlow
    private val _sharedEvent: MutableSharedFlow<String> = MutableSharedFlow()
    val sharedEvent: SharedFlow<String> = _sharedEvent.asSharedFlow()

    fun triggerSharedEvent() {
        viewModelScope.launch {
            _sharedEvent.emit("Hello")
        }
    }
    // endregion

    // region singleLiveEvent
    private val _singleEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val singleEvent: LiveData<String> = _singleEvent

    fun triggerSingleEvent() {
        _singleEvent.value = "Hello"
    }
    // endregion

    // region mutableLiveData
    private val _liveEvent: MutableLiveData<String> = MutableLiveData()
    val liveEvent: LiveData<String> = _liveEvent

    fun triggerLiveEvent(){
        _liveEvent.value = "Hello"
    }
    // endregion
}

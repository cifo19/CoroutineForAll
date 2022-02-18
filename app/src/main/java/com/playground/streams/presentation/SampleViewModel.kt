package com.playground.streams.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.playground.streams.presentation.data.SampleEvent
import com.playground.streams.presentation.data.SampleState
import com.playground.streams.presentation.data.UIEvent
import com.playground.streams.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor() : ViewModel() {

    // region LiveData
    private val _sampleEventLiveData = SingleLiveEvent<SampleEvent>()
    val sampleEventLiveData: LiveData<SampleEvent> = _sampleEventLiveData

    private val _sampleStateLiveData = MutableLiveData<SampleState>()
    val sampleStateLiveData: LiveData<SampleState> = _sampleStateLiveData
    // endregion

    // region Flow
    private val _sampleEventChannel = Channel<SampleEvent>(capacity = Channel.BUFFERED)
    val sampleEventFlow = _sampleEventChannel.receiveAsFlow()

    private val _sampleStateStateFlow = MutableStateFlow(SampleState(""))
    val sampleStateStateFlow = _sampleStateStateFlow.asStateFlow()
    // endregion

    fun onUIEvent(uiEvent: UIEvent) {
        when(uiEvent) {
            UIEvent.TRIGGER_ONE_SHOW_EVENT -> {
                _sampleEventLiveData.value = SampleEvent("Foo")
                _sampleEventChannel.trySend(SampleEvent("Foo"))
            }
            UIEvent.UPDATE_STATE -> {
                _sampleStateLiveData.value = SampleState("Foo")
                _sampleStateStateFlow.value = SampleState("Foo")
            }
        }
    }
}

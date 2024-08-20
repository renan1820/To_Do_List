package com.cubostecnologia.zigpdvandroid.core.compose

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class UIStateFlowSender<T> {
    private val uiStateChannel = Channel<T>(Channel.BUFFERED)
    val valueFlow: Flow<T> = uiStateChannel.receiveAsFlow()
    var value: T? = null

    fun getEventsFlow(): Flow<T> = uiStateChannel.receiveAsFlow()

    fun sender(scope: CoroutineScope, uiState: T): Job = scope.launch {
        value = uiState
        value?.let { uiStateChannel.send(it) }
    }
}

fun <T> Flow<T>.collectState(scope: CoroutineScope, flowCollector: FlowCollector<T>) {
    scope.launch(Dispatchers.Main) {
        this@collectState.collect(flowCollector)
    }
}

fun <T> Flow<T>.collectState(
    lifecycle: Lifecycle,
    state: Lifecycle.State,
    flowCollector: FlowCollector<T>
) {
    lifecycle.coroutineScope.launch(Dispatchers.Main) {
        this@collectState.flowWithLifecycle(lifecycle, state).collect(flowCollector)
    }
}
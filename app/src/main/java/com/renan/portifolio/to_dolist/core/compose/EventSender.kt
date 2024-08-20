package com.cubostecnologia.zigpdvandroid.core.compose

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface EventSender<T> {
    val eventsFlow: Flow<T>
    fun CoroutineScope.sendEvent(event: T): Job
}

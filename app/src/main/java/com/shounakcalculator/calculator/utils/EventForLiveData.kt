package com.shounakcalculator.calculator.utils

import androidx.lifecycle.Observer

open class EventForLiveData<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    @Synchronized
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled)
            null
        else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) :
    Observer<EventForLiveData<T>> {
    override fun onChanged(eventForLiveData: EventForLiveData<T>?) {
        eventForLiveData?.getContentIfNotHandled()?.let(onEventUnhandledContent)
    }
}

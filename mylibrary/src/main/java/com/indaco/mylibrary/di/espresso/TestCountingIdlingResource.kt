package com.indaco.mylibrary.di.espresso

import androidx.test.espresso.idling.CountingIdlingResource

object TestCountingIdlingResource {

    private const val RES = "GLOBAL"

    @JvmField val countingIdlingResource = CountingIdlingResource(RES)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}
package com.irfan.auto1

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.coroutines.ContinuationInterceptor

/** coroutine test rul */
class CoroutineTestRule:TestWatcher() {
    private val dispatcher = UnconfinedTestDispatcher()

    override fun starting(description: Description?) {
        super.starting(description)
       Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
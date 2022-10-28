package com.irfan.auto1.common
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import org.junit.Rule


abstract class BaseTest {


    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineRul = CoroutineTestRule()

     /**
      * Initialise mockK annotation before running each test
      * */
    open fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
         init()
    }

    open fun init(){
      // override in tests
    }

    /**
     * Check where a failure is with input message
     * */
    fun <T> isFailureWithMessage(result: Result<T>, message: String): Boolean {
        var errorMessage: String = "#-#"
        result.onFailure { errorMessage = it.message!! }
        return result.isFailure && errorMessage.contains(message)
    }
}
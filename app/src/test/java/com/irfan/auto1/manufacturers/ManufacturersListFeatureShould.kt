package com.irfan.auto1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class ManufacturersListFeatureShould {

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineRul = CoroutineTestRule()


    private lateinit var  uiController: ManufacturersSpyUiController

    @Before
   fun setup(){
        val remoteService = ManufacturersRemoteService()
        val manufacturersRepo = ManufacturersRepo(remoteService,Mapper())
        val fetchManufacturersUseCase = FetchManufacturersUseCase(manufacturersRepo)
        val manufacturersViewModel = ManufacturersViewModel(fetchManufacturersUseCase)
       uiController = ManufacturersSpyUiController().apply { viewModel = manufacturersViewModel }
       uiController.onCreate()
   }


    @Test
    fun fetchManufacturersList(){
        val actual = listOf("StatLoading","Success")
        uiController.fetchManufacturers()
        val result = uiController.uiStates
        assertThat(result).isEqualTo(actual)
    }
}

class ManufacturersSpyUiController:LifecycleOwner {

    lateinit var viewModel: ManufacturersViewModel
    val uiStates = mutableListOf<String>()
    private val countDownLatch: CountDownLatch = CountDownLatch(1)


    private val registry: LifecycleRegistry by lazy { LifecycleRegistry(this) }
    override fun getLifecycle() = registry

    fun onCreate(){
        registry.currentState = Lifecycle.State.STARTED
        viewModel.uiState.observe(this) {
            uiStates.add(it)
            if (uiStates.size == 2) {
                countDownLatch.countDown()
            }
        }
    }


    fun fetchManufacturers() {
        viewModel.fetchManufacturers()
        countDownLatch.await(100, TimeUnit.MILLISECONDS)

    }

}
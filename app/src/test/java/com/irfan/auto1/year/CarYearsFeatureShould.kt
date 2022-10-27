package com.irfan.auto1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.year.CarYearsRepository
import com.irfan.auto1.year.CarYearsUiState
import com.irfan.auto1.year.CarYearsViewModel
import com.irfan.auto1.year.FetchCarYearsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class CarYearsFeatureShould {

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineRul = CoroutineTestRule()

    private lateinit var uiController: CarYearsSpyUiController

    @Before
    fun setup() {

        val carYearsRepository = CarYearsRepository()
        val fetchCarYearsUseCase = FetchCarYearsUseCase(carYearsRepository)
        val carYearsViewModel = CarYearsViewModel(fetchCarYearsUseCase)
        uiController = CarYearsSpyUiController().apply { viewModel = carYearsViewModel }
        uiController.onCreate()
    }


    @Test
    fun fetchCarYearsCarYearsList() = runTest {
        val actual = listOf(
            CarYearsUiState(loading = true),
            CarYearsUiState(carYears = TestDataProvider.getYearsAsDomainModels())
        )
        uiController.fetchCarYears()
        val result = uiController.uiStates
        assertThat(result).isEqualTo(actual)
    }
}

class CarYearsSpyUiController : LifecycleOwner {

    lateinit var viewModel: CarYearsViewModel
    val uiStates = mutableListOf<CarYearsUiState>()
    private val countDownLatch: CountDownLatch = CountDownLatch(1)


    private val registry: LifecycleRegistry by lazy { LifecycleRegistry(this) }
    override fun getLifecycle() = registry

    fun onCreate() {
        registry.currentState = Lifecycle.State.STARTED
        viewModel.uiStateUpdater.observe(this) {
            uiStates.add(it)
            if (uiStates.size == 2) {
                countDownLatch.countDown()
            }
        }
    }


    fun fetchCarYears() {
        viewModel.fetchCarYears()
        countDownLatch.await(500, TimeUnit.MILLISECONDS)
    }

}
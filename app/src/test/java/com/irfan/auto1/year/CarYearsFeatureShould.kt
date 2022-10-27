package com.irfan.auto1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.year.data.remote.CarYearsRemoteDataSource
import com.irfan.auto1.year.data.CarYearsRepository
import com.irfan.auto1.year.data.remote.CarYearsRemoteApi
import com.irfan.auto1.year.domain.mapper.CarYearsMapper
import com.irfan.auto1.year.domain.model.CarInfo
import com.irfan.auto1.year.domain.usecase.FetchCarYearsUseCase
import com.irfan.auto1.year.ui.CarYearsUiState
import com.irfan.auto1.year.ui.CarYearsViewModel
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class CarYearsFeatureShould {

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineRul = CoroutineTestRule()

    private lateinit var uiController: CarYearsSpyUiController

    private val remoteApi = object : CarYearsRemoteApi {
        override suspend fun fetchCarYears(manufacturer:Int, carType:String): Response<ResponseBody> {
            val contentType = "application/json; charset=utf-8".toMediaType()
            return Response.success(TestDataProvider.getCarYearResponseJson().toResponseBody(contentType))
        }

    }

    @Before
    fun setup() {

        val mapper = CarYearsMapper()
        val carYearsRemoteDataSource = CarYearsRemoteDataSource(remoteApi)
        val carYearsRepository = CarYearsRepository(carYearsRemoteDataSource, mapper)
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
        viewModel.fetchCarYears(CarInfo())
        countDownLatch.await(500, TimeUnit.MILLISECONDS)
    }

}
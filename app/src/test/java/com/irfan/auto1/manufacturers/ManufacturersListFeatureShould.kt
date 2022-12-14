package com.irfan.auto1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.common.CoroutineTestRule
import com.irfan.auto1.common.TestDataProvider
import com.irfan.auto1.manufacturers.*
import com.irfan.auto1.manufacturers.data.remote.ManufacturersRemoteAPI
import com.irfan.auto1.manufacturers.data.ManufacturersRepo
import com.irfan.auto1.manufacturers.data.remote.ManufacturersRemoteDataSource
import com.irfan.auto1.manufacturers.data.remote.PagingManager
import com.irfan.auto1.manufacturers.domain.mapper.ManufacturersMapper
import com.irfan.auto1.manufacturers.ui.CarManufacturerUiState
import com.irfan.auto1.manufacturers.ui.CarManufacturersViewModel
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

class ManufacturersListFeatureShould {

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineRul = CoroutineTestRule()


    private lateinit var  uiController: ManufacturersSpyUiController

    private val remoteAPI = object : ManufacturersRemoteAPI {
        override suspend fun getManufacturers(nextPage: Int, pageSize: Int): Response<ResponseBody> {
            val jsonData = TestDataProvider.getManufacturerResponseJson()
            val contentType = "application/json; charset=utf-8".toMediaType()
            return Response.success(jsonData.toResponseBody(contentType))
        }

    }


    @Before
   fun setup(){
        val pagingManager = PagingManager()
        val remoteService = ManufacturersRemoteDataSource(remoteAPI, pagingManager)
        val manufacturersRepo = ManufacturersRepo(remoteService, ManufacturersMapper())
        val fetchManufacturersUseCase = FetchManufacturersUseCase(manufacturersRepo)
        val manufacturersViewModel = CarManufacturersViewModel(fetchManufacturersUseCase)
       uiController = ManufacturersSpyUiController().apply { viewModelCar = manufacturersViewModel }
       uiController.onCreate()
   }


    @Test
    fun fetchManufacturersList()= runTest{
        val actual = listOf(
            CarManufacturerUiState(loading = true),
            CarManufacturerUiState(data = TestDataProvider.getManufacturersAsDomainModels())
        )
        uiController.fetchManufacturers()
        val result = uiController.uiStates
        assertThat(result).isEqualTo(actual)
    }
}

class ManufacturersSpyUiController:LifecycleOwner {

    lateinit var viewModelCar: CarManufacturersViewModel
    val uiStates = mutableListOf<CarManufacturerUiState>()
    private val countDownLatch: CountDownLatch = CountDownLatch(1)


    private val registry: LifecycleRegistry by lazy { LifecycleRegistry(this) }
    override fun getLifecycle() = registry

    fun onCreate(){
        registry.currentState = Lifecycle.State.STARTED
        viewModelCar.uiStateUpdater.observe(this) {
            uiStates.add(it)
            if (uiStates.size == 2) {
                countDownLatch.countDown()
            }
        }
    }


    fun fetchManufacturers() {
        viewModelCar.fetchManufacturers()
        countDownLatch.await(500, TimeUnit.MILLISECONDS)

    }

}
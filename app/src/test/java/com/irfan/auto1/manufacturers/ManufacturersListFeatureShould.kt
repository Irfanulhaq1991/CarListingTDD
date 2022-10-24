package com.irfan.auto1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.google.common.truth.Truth.assertThat
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

    private val remoteAPI = object :ManufacturersRemoteAPI{
        override suspend fun getManufacturers(): Response<ResponseBody> {
            val jsonData = TestDataProvider.getManufacturersResponseJson()
            val contentType = "application/json; charset=utf-8".toMediaType()
            return Response.success(jsonData.toResponseBody(contentType))
        }

    }


    @Before
   fun setup(){
        val remoteService = ManufacturersRemoteService(remoteAPI)
        val manufacturersRepo = ManufacturersRepo(remoteService,JsonToDomainManufacturersMapper())
        val fetchManufacturersUseCase = FetchManufacturersUseCase(manufacturersRepo)
        val manufacturersViewModel = ManufacturersViewModel(fetchManufacturersUseCase)
       uiController = ManufacturersSpyUiController().apply { viewModel = manufacturersViewModel }
       uiController.onCreate()
   }


    @Test
    fun fetchManufacturersList()= runTest{
        val actual = listOf(ManufacturerUiState(showLoading = true),ManufacturerUiState(manufacturers = TestDataProvider.getManufacturersAsDomainModels()))
        uiController.fetchManufacturers()
        val result = uiController.uiStates
        assertThat(result).isEqualTo(actual)
    }
}

class ManufacturersSpyUiController:LifecycleOwner {

    lateinit var viewModel: ManufacturersViewModel
    val uiStates = mutableListOf<ManufacturerUiState>()
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
        countDownLatch.await(500, TimeUnit.MILLISECONDS)

    }

}
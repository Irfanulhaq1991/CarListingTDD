package com.irfan.auto1.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.BaseTest
import com.irfan.auto1.CoroutineTestRule
import com.irfan.auto1.TestDataProvider
import com.irfan.auto1.model.data.ModelFilter
import com.irfan.auto1.model.data.remote.ModelRemoteApi
import com.irfan.auto1.model.data.remote.ModelsRemoteDataSource
import com.irfan.auto1.model.data.ModelsRepository
import com.irfan.auto1.model.domain.usecase.FetchModelsUseCase
import com.irfan.auto1.model.domain.mapper.ModelsMapper
import com.irfan.auto1.model.domain.model.Model
import com.irfan.auto1.model.domain.usecase.SearchModelsUseCase
import com.irfan.auto1.model.ui.ModelUiState
import com.irfan.auto1.model.ui.ModelsViewModel
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

class ModelListFeatureShould {
    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRul = CoroutineTestRule()

    private lateinit var uiController: ModelListSpyUiController
    private val remoteApi = object : ModelRemoteApi {
        override suspend fun fetchModels(manufacturerId: Int): Response<ResponseBody> {
            val contentType = "application/json; charset=utf-8".toMediaType()

            return Response.success(
                TestDataProvider.getModelResponseJson().toResponseBody(contentType)
            )
        }

    }
    private lateinit var modelFilter: ModelFilter

    @Before
    fun setup() {
        val mapper = ModelsMapper()
        val remoteDataSource = ModelsRemoteDataSource(remoteApi)
        modelFilter = ModelFilter()
        val repo = ModelsRepository(mapper, remoteDataSource, modelFilter)
        val fetchModelsUseCase = FetchModelsUseCase(repo)
        val searchModelsUseCase = SearchModelsUseCase(repo)
        val modelsViewModel = ModelsViewModel(fetchModelsUseCase, searchModelsUseCase)
        uiController = ModelListSpyUiController().apply { viewModel = modelsViewModel }
        uiController.onCreate()
    }


    @Test
    fun fetchModelsList() {
        val expected = listOf(
            ModelUiState(loading = true),
            ModelUiState(data = TestDataProvider.getModelAsDomainModels())
        )
        uiController.fetchModel()
        val actual = uiController.uiStates
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun search() {
        val expected = listOf(
            ModelUiState(update = true, data = TestDataProvider.getModelAsDomainModels()
                    .filter { it.name.contains("e") }))

        modelFilter.setSearchData(TestDataProvider.getModelAsDomainModels())
        uiController.search("e")
        val actual = uiController.uiStates
        assertThat(actual).isEqualTo(expected)
    }
}

class ModelListSpyUiController : LifecycleOwner {

    lateinit var viewModel: ModelsViewModel
    val uiStates = mutableListOf<ModelUiState>()
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


    fun fetchModel() {
        viewModel.fetchModels(0)
        countDownLatch.await(500, TimeUnit.MILLISECONDS)

    }

    fun search(query: String) {
        viewModel.search(query)
        countDownLatch.await(500, TimeUnit.MILLISECONDS)
    }

}
package com.irfan.auto1.model

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.BaseTest
import com.irfan.auto1.TestDataProvider
import com.irfan.auto1.model.data.remote.ModelRemoteApi
import com.irfan.auto1.model.data.remote.ModelsRemoteDataSource
import com.irfan.auto1.model.data.ModelsRepository
import com.irfan.auto1.model.domain.usecase.FetchModelsUseCase
import com.irfan.auto1.model.domain.mapper.ModelsMapper
import com.irfan.auto1.model.ui.ModelUiState
import com.irfan.auto1.model.ui.ModelsViewModel
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class ModelListFeatureShould : BaseTest() {


    private lateinit var uiController: ModelListSpyUiController
    private val remoteApi = object : ModelRemoteApi {
        override suspend fun fetchModels(manufacturerId:Int): Response<ResponseBody> {
            val contentType = "application/json; charset=utf-8".toMediaType()

            return Response.success(
                TestDataProvider.getModelResponseJson().toResponseBody(contentType)
            )
        }

    }

    @Before
    override fun setup() {
        val mapper = ModelsMapper()
        val remoteDataSource = ModelsRemoteDataSource(remoteApi)
        val repo = ModelsRepository(mapper, remoteDataSource)
        val useCase = FetchModelsUseCase(repo)
        val modelsViewModel = ModelsViewModel(useCase)
        uiController = ModelListSpyUiController().apply { viewModel = modelsViewModel }
        uiController.onCreate()
    }


    @Test
    fun fetchModelsList() = runTest {
        val actual = listOf(
            ModelUiState(loading = true),
            ModelUiState(models = TestDataProvider.getModelAsDomainModels())
        )
        uiController.fetchModel()
        val result = uiController.uiStates
        assertThat(result).isEqualTo(actual)
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

}
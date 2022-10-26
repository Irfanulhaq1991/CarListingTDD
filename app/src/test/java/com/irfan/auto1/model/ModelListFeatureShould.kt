package com.irfan.auto1.model

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.BaseTest
import com.irfan.auto1.TestDataProvider
import com.irfan.auto1.manufacturers.ui.ManufacturerUiState
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class ModelListFeatureShould : BaseTest() {


    private lateinit var uiController: ModelListSpyUiController


    @Before
    override fun setup() {
        val useCase = FetchModelsUseCase()
        val modelsViewModel = ModelsViewModel(useCase)
        uiController = ModelListSpyUiController().apply { viewModel = modelsViewModel }
        uiController.onCreate()
    }


    @Test
    fun fetchModelsList() = runTest {
        val actual = listOf(
            ManufacturerUiState(loading = true),
            ModelUiState(manufacturers = TestDataProvider.getModelAsDomainModels())
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
        viewModel.fetchModels()
        countDownLatch.await(500, TimeUnit.MILLISECONDS)

    }

}
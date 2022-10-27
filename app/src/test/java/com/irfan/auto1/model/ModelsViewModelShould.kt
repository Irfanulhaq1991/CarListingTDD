package com.irfan.auto1.model

import com.irfan.auto1.BaseTest
import com.irfan.auto1.model.domain.usecase.SearchModelsUseCase
import com.irfan.auto1.model.domain.usecase.FetchModelsUseCase
import com.irfan.auto1.model.ui.ModelsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any

class ModelsViewModelShould : BaseTest() {

    @RelaxedMockK
    private lateinit var fetchModelsUseCase: FetchModelsUseCase

    @RelaxedMockK
    private lateinit var searchModelsUseCase: SearchModelsUseCase
    private lateinit var viewModel: ModelsViewModel

    @Before
    override fun setup() {
        super.setup()
        viewModel = ModelsViewModel(fetchModelsUseCase,searchModelsUseCase)
    }


    @Test
    fun callFetchModelsUseCase() = runTest {
        coEvery { fetchModelsUseCase(any()) } answers { Result.success(emptyList()) }
        viewModel.fetchModels(any())
        coVerify { fetchModelsUseCase.invoke(any()) }
    }

    @Test
    fun callSearchModelsUseCase() = runTest {
        coEvery { searchModelsUseCase(any()) } answers { Result.success(emptyList()) }
        viewModel.search("##")
        coVerify { searchModelsUseCase.invoke(any()) }
    }
}
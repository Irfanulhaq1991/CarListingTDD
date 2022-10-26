package com.irfan.auto1.model

import com.irfan.auto1.BaseTest
import com.irfan.auto1.model.domain.usecase.FetchModelsUseCase
import com.irfan.auto1.model.ui.ModelsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ModelsViewModelShould : BaseTest(){

    @RelaxedMockK
    private lateinit var useCase: FetchModelsUseCase
    private lateinit var viewModel: ModelsViewModel

    @Before
    override fun setup() {
        super.setup()
        viewModel = ModelsViewModel(useCase)
    }




    @Test
    fun callFetchModelsUseCase()= runTest{
        coEvery { useCase(any()) } answers { Result.success(emptyList())}
        viewModel.fetchModels(0)
        coVerify { useCase.invoke(any())}
    }

}
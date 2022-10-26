package com.irfan.auto1.model

import com.irfan.auto1.BaseTest
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
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
    fun shouldCallFetchModelsUseCase(){
        viewModel.fetchModels()
        coVerify { useCase.invoke()}
    }

}
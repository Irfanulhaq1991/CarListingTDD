package com.irfan.auto1.model

import com.irfan.auto1.common.BaseTest
import com.irfan.auto1.common.CarInfo
import com.irfan.auto1.model.domain.usecase.SearchModelsUseCase
import com.irfan.auto1.model.domain.usecase.FetchModelsUseCase
import com.irfan.auto1.model.ui.CarModelsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ModelsViewCarModelShould : BaseTest() {

    @RelaxedMockK
    private lateinit var fetchModelsUseCase: FetchModelsUseCase

    @RelaxedMockK
    private lateinit var searchModelsUseCase: SearchModelsUseCase
    private lateinit var viewModelCar: CarModelsViewModel

    @Before
    override fun setup() {
        super.setup()
        viewModelCar = CarModelsViewModel(fetchModelsUseCase,searchModelsUseCase)
    }


    @Test
    fun callFetchModelsUseCase() = runTest {
        coEvery { fetchModelsUseCase(any()) } answers { Result.success(emptyList()) }
        viewModelCar.doFetching(CarInfo())
        coVerify { fetchModelsUseCase.invoke(any()) }
    }

    @Test
    fun callSearchModelsUseCase() = runTest {
        coEvery { searchModelsUseCase(any()) } answers { Result.success(emptyList()) }
        viewModelCar.search("##")
        coVerify { searchModelsUseCase.invoke(any()) }
    }
}
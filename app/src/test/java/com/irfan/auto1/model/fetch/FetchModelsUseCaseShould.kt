package com.irfan.auto1.model.fetch

import com.irfan.auto1.common.BaseTest
import com.irfan.auto1.common.CarInfo
import com.irfan.auto1.model.data.ModelsRepository
import com.irfan.auto1.model.domain.usecase.FetchModelsUseCase
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FetchModelsUseCaseShould : BaseTest() {

    @RelaxedMockK
    private lateinit var repo: ModelsRepository
    private lateinit var useCase: FetchModelsUseCase

    @Before
    override fun setup(){
        super.setup()
        useCase = FetchModelsUseCase(repo)
    }

    @Test
    fun callRepository() = runTest{
        useCase(CarInfo())
        coVerify { repo.fetchModels(any()) }
    }
}
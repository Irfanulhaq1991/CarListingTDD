package com.irfan.auto1.model.search

import com.irfan.auto1.common.BaseTest
import com.irfan.auto1.model.data.ModelsRepository
import com.irfan.auto1.model.domain.usecase.SearchModelsUseCase
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any

class SearchModelsUseCaseShould : BaseTest() {

    @RelaxedMockK
    private lateinit var repo: ModelsRepository

    private lateinit var searchModelsUseCase: SearchModelsUseCase

    @Before
    override fun setup() {
        super.setup()
        searchModelsUseCase = SearchModelsUseCase(repo)
    }
    @Test
    fun callRepository() = runTest{
        searchModelsUseCase(any())
        coVerify { repo.search(any()) }
    }
}
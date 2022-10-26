package com.irfan.auto1.model

import com.irfan.auto1.BaseTest
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class FetchModelsUseCaseShould : BaseTest() {

    @RelaxedMockK
    private lateinit var repo: FetchModelsRepository
    private lateinit var useCase: FetchModelsUseCase

    @Before
    override fun setup(){
        super.setup()
        useCase = FetchModelsUseCase(repo)
    }

    @Test
    fun callRepository() {
        useCase()
        verify { repo.fetchModels() }
    }
}
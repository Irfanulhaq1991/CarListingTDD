package com.irfan.auto1.year

import com.irfan.auto1.BaseTest
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class FetchCarYearsUseCaseShould : BaseTest() {

    @RelaxedMockK
    private lateinit var repo: CarYearsRepository
    private lateinit var fetchCarYearsUseCase: FetchCarYearsUseCase


    @Before
    override fun setup() {
        super.setup()
        fetchCarYearsUseCase = FetchCarYearsUseCase(repo)
    }

    @Test
    fun callCarYearsRepository() {
        fetchCarYearsUseCase()
        verify { repo.fetchCarYears() }
    }
}
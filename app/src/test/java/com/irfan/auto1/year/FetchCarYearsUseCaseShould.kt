package com.irfan.auto1.year

import com.irfan.auto1.BaseTest
import com.irfan.auto1.year.data.CarYearsRepository
import com.irfan.auto1.year.domain.usecase.FetchCarYearsUseCase
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any

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
    fun callCarYearsRepository() = runTest{
        fetchCarYearsUseCase(any())
        coVerify { repo.fetchCarYears(any()) }
    }
}
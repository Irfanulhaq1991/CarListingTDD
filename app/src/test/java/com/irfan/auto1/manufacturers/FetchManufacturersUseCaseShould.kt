package com.irfan.auto1.manufacturers

import com.irfan.auto1.BaseTest
import com.irfan.auto1.FetchManufacturersUseCase
import com.irfan.auto1.ManufacturersRepo
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FetchManufacturersUseCaseShould : BaseTest() {


    @RelaxedMockK
    private lateinit var manufacturersRepo: ManufacturersRepo
    private lateinit var fetchManufacturersUseCase : FetchManufacturersUseCase

    @Before
    override fun setup() {
        super.setup()
        fetchManufacturersUseCase = FetchManufacturersUseCase(manufacturersRepo)
    }

    @Test
    fun fetchManufacturers() = runTest{
        fetchManufacturersUseCase()
        coVerify { manufacturersRepo.fetchManufacturers() }
    }
}
package com.irfan.auto1

import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import junit.framework.TestCase
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
    fun fetchManufacturers() {
        fetchManufacturersUseCase()
        verify { manufacturersRepo.fetchManufacturers() }
    }
}
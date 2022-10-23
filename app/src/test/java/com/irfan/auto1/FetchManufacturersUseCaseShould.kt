package com.irfan.auto1

import io.mockk.verify
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class FetchManufacturersUseCaseShould : BaseTest() {


    private lateinit var manufacturersRepo: ManufacturersRepo
    private lateinit var fetchManufacturersUseCase : FetchManufacturersUseCase

    @Before
    override fun setup() {
        super.setup()
        fetchManufacturersUseCase = FetchManufacturersUseCase()
    }

    @Test
    fun fetchManufacturers() {
        val fetchManufacturersUseCase = FetchManufacturersUseCase()
        fetchManufacturersUseCase()
        verify { manufacturersRepo.fetchManufacturers() }
    }
}
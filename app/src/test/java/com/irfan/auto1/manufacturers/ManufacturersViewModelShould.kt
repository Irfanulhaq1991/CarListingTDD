package com.irfan.auto1.manufacturers

import com.irfan.auto1.BaseTest
import com.irfan.auto1.FetchManufacturersUseCase
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ManufacturersViewModelShould : BaseTest() {

    @RelaxedMockK
    private lateinit var fetchManufactureUseCase: FetchManufacturersUseCase

    @Before
    override fun setup() {
        super.setup()
    }

    @Test
    fun fetchManufacturers() {
        verify { fetchManufactureUseCase }
    }
}
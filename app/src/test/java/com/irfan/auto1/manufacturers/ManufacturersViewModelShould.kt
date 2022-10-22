package com.irfan.auto1.manufacturers

import com.irfan.auto1.BaseTest
import com.irfan.auto1.FetchManufacturersUseCase
import com.irfan.auto1.ManufacturersViewModel
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ManufacturersViewModelShould : BaseTest() {

    @RelaxedMockK
    private lateinit var fetchManufactureUseCase: FetchManufacturersUseCase
    private lateinit var viewModel: ManufacturersViewModel

    @Before
    override fun setup() {
        super.setup()
        viewModel = ManufacturersViewModel()
    }

    @Test
    fun fetchManufacturers() {
        viewModel.fetchManufacturers()
        verify { fetchManufactureUseCase }
    }
}
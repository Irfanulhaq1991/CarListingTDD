package com.irfan.auto1.year

import com.irfan.auto1.BaseTest
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class CarYearsViewModelShould : BaseTest() {

    private lateinit var viewModel: CarYearsViewModel

    @RelaxedMockK
    private lateinit var fetchCarYearsUseCase: FetchCarYearsUseCase

    @Before
    override fun setup() {
        super.setup()
        viewModel = CarYearsViewModel(fetchCarYearsUseCase)
    }


    @Test
    fun callFetchCarYearsUseCase() {
        viewModel.fetchCarYears()
        verify { fetchCarYearsUseCase() }
    }
}
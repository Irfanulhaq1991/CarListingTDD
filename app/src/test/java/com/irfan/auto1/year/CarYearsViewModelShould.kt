package com.irfan.auto1.year

import com.irfan.auto1.BaseTest
import com.irfan.auto1.common.CarInfo
import com.irfan.auto1.year.domain.usecase.FetchCarYearsUseCase
import com.irfan.auto1.year.ui.CarYearsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
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
    fun callFetchCarYearsUseCase() = runTest {
        coEvery { fetchCarYearsUseCase(any()) } answers { Result.success(emptyList())}
        viewModel.fetchCarYears(CarInfo())
        coVerify { fetchCarYearsUseCase(any()) }
    }
}
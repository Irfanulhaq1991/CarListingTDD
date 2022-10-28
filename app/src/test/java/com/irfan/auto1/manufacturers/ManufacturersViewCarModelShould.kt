package com.irfan.auto1.manufacturers

import com.irfan.auto1.common.BaseTest
import com.irfan.auto1.common.TestDataProvider
import com.irfan.auto1.manufacturers.ui.CarManufacturersViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ManufacturersViewCarModelShould : BaseTest() {

    @MockK
    private lateinit var fetchManufactureUseCase: FetchManufacturersUseCase
    private lateinit var viewModelCar: CarManufacturersViewModel


    @Before
    override fun setup() {
        super.setup()
        viewModelCar = CarManufacturersViewModel(fetchManufactureUseCase)
    }

    @Test
    fun fetchManufacturers() = runTest{
        coEvery { fetchManufactureUseCase() } answers { Result.success(TestDataProvider.getManufacturersAsDomainModels())}
        viewModelCar.fetchManufacturers()
        coVerify { fetchManufactureUseCase() }
    }
}
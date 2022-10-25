package com.irfan.auto1.manufacturers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.irfan.auto1.*
import com.irfan.auto1.manufacturers.ui.ManufacturersViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ManufacturersViewModelShould : BaseTest() {

    @MockK
    private lateinit var fetchManufactureUseCase: FetchManufacturersUseCase
    private lateinit var viewModel: ManufacturersViewModel

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineRul = CoroutineTestRule()


    @Before
    override fun setup() {
        super.setup()
        viewModel = ManufacturersViewModel(fetchManufactureUseCase)
    }

    @Test
    fun fetchManufacturers() = runTest{
        coEvery { fetchManufactureUseCase() } answers { Result.success(TestDataProvider.getManufacturersAsDomainModels())}
        viewModel.fetchManufacturers()
        coVerify { fetchManufactureUseCase() }
    }
}
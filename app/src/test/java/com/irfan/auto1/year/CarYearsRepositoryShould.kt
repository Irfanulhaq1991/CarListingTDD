package com.irfan.auto1.year

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.BaseTest
import com.irfan.auto1.year.data.remote.CarYearsRemoteDataSource
import com.irfan.auto1.year.data.CarYearsRepository
import com.irfan.auto1.year.domain.model.CarYear
import com.irfan.auto1.year.domain.mapper.CarYearsMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any

class CarYearsRepositoryShould : BaseTest() {


    @RelaxedMockK
    private lateinit var mapper: CarYearsMapper

    @RelaxedMockK
    private lateinit var carYearsRemoteDataSource: CarYearsRemoteDataSource
    private lateinit var repository: CarYearsRepository

    @Before
    override fun setup() {
        super.setup()
        repository = CarYearsRepository(carYearsRemoteDataSource, mapper)
    }

    @Test
    fun callCarYearsRemoteDataSource() = runTest {
        coEvery { carYearsRemoteDataSource.fetchCarYears(any()) } answers { Result.success(emptyList())}
        repository.fetchCarYears(any())
        coVerify { carYearsRemoteDataSource.fetchCarYears(any()) }

    }

    @Test
    fun callCarYearsMapper() = runTest {
        coEvery { carYearsRemoteDataSource.fetchCarYears(any()) } answers { Result.success(emptyList())}
        repository.fetchCarYears(any())
        coVerify { mapper.map(any()) }

    }

    @Test
    fun returnCarYearsDomain() = runTest {
        coEvery { carYearsRemoteDataSource.fetchCarYears(any()) } answers { Result.success(emptyList())}
       val actual =  repository.fetchCarYears(any())
        assertThat(actual).isEqualTo(Result.success(emptyList<CarYear>()))
    }

    @Test
    fun notAlterErrorMessage() = runTest{
        val errorMessage = "###"
        coEvery { carYearsRemoteDataSource.fetchCarYears(any()) } answers { Result.failure(Throwable(errorMessage))}
        val actual =  isFailureWithMessage(repository.fetchCarYears(any()),errorMessage)
        assertThat(actual).isTrue()

    }
}
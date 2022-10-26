package com.irfan.auto1.manufacturers

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.*
import com.irfan.auto1.manufacturers.data.remote.IManufacturersRemoteDataSource
import com.irfan.auto1.manufacturers.data.remote.ManufacturerDto
import com.irfan.auto1.manufacturers.data.ManufacturersRepo
import com.irfan.auto1.manufacturers.domain.mapper.ManufacturersMapper
import com.irfan.auto1.manufacturers.domain.model.Manufacturer
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ManufacturersRepoShould : BaseTest() {


    @RelaxedMockK
    private lateinit var manufacturersRemoteDataSource: IManufacturersRemoteDataSource
    private lateinit var manufacturersRepo: ManufacturersRepo

    @RelaxedMockK
    private lateinit var manufacturersMapper: ManufacturersMapper

    @Before
    override fun setup() {
        super.setup()
        manufacturersRepo =
            ManufacturersRepo(manufacturersRemoteDataSource, manufacturersMapper)
    }

    @Test
    fun notAlterError() = runTest{
        val errorMessage = "###"
        coEvery { manufacturersRemoteDataSource.fetchManufacturers() } answers {
            Result.failure(
                Throwable(
                    errorMessage
                )
            )
        }
        val fetchManufacturers = manufacturersRepo.fetchManufacturers()
        val actual = isFailureWithMessage(fetchManufacturers, errorMessage)
        assertThat(actual).isTrue()
    }

    @Test
    fun fetchDomainManufacturers() = runTest{
        val manufacturersDto = TestDataProvider.getManufacturersAsDto()
        val manufacturerDomain = TestDataProvider.getManufacturersAsDomainModels()
        coEvery { manufacturersRemoteDataSource.fetchManufacturers() } answers {
            Result.success(
                manufacturersDto
            )
        }
        coEvery { manufacturersMapper.map(any()) } answers { manufacturerDomain }
        assertThat(manufacturersRepo.fetchManufacturers()).isEqualTo(
            Result.success(
                manufacturerDomain
            )
        )
    }

    @Test
    fun returnErrorOnZeroManufacturers()= runTest {
        val manufacturersDtos = emptyList<ManufacturerDto>()
        val manufacturerDomain = emptyList<Manufacturer>()
        coEvery { manufacturersRemoteDataSource.fetchManufacturers() } answers {
            Result.success(manufacturersDtos)
        }
        coEvery { manufacturersMapper.map(any()) } answers { manufacturerDomain }
        val actual =
            isFailureWithMessage(manufacturersRepo.fetchManufacturers(), "No Manufacturer Found")
            assertThat(actual).isTrue()

    }
}
package com.irfan.auto1.manufacturers

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.common.BaseTest
import com.irfan.auto1.common.TestDataProvider
import com.irfan.auto1.manufacturers.data.ManufacturersRepo
import com.irfan.auto1.manufacturers.data.remote.ManufacturerDto
import com.irfan.auto1.manufacturers.data.remote.RemoteDataSource
import com.irfan.auto1.manufacturers.domain.mapper.ManufacturersMapper
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ManufacturersRepoShould : BaseTest() {


    @RelaxedMockK
    private lateinit var remoteDataSource: RemoteDataSource<ManufacturerDto>
    private lateinit var manufacturersRepo: ManufacturersRepo

    @RelaxedMockK
    private lateinit var manufacturersMapper: ManufacturersMapper

    @Before
    override fun setup() {
        super.setup()
        manufacturersRepo =
            ManufacturersRepo(remoteDataSource, manufacturersMapper)
    }

    @Test
    fun notAlterError() = runTest{
        val errorMessage = "###"
        coEvery { remoteDataSource.doFetching() } answers {
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
    fun returnDomainManufacturers() = runTest{
        val manufacturersDto = TestDataProvider.getManufacturersAsDto()
        val manufacturerDomain = TestDataProvider.getManufacturersAsDomainModels()
        coEvery { remoteDataSource.doFetching() } answers {
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
}
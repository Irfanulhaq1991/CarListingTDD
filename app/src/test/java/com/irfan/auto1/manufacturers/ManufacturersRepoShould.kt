package com.irfan.auto1.manufacturers

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.*
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.json.JSONObject
import org.junit.Before
import org.junit.Test

class ManufacturersRepoShould : BaseTest() {


    @RelaxedMockK
    private lateinit var manufacturersRemoteService: IManufacturersRemoteService<JSONObject>
    private lateinit var manufacturersRepo: ManufacturersRepo

    @RelaxedMockK
    private lateinit var jsonToDomainManufacturersMapper: JsonToDomainManufacturersMapper

    @Before
    override fun setup() {
        super.setup()
        manufacturersRepo =
            ManufacturersRepo(manufacturersRemoteService, jsonToDomainManufacturersMapper)
    }

    @Test
    fun notAlterError() = runTest{
        val errorMessage = "###"
        coEvery { manufacturersRemoteService.fetchManufacturers() } answers {
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
        val manufacturersJson = TestDataProvider.getManufacturersAsJsonStrings()
        val manufacturerDomain = TestDataProvider.getManufacturersAsDomainModels()
        coEvery { manufacturersRemoteService.fetchManufacturers() } answers {
            Result.success(
                JSONObject(manufacturersJson)
            )
        }
        every { jsonToDomainManufacturersMapper.map(any()) } answers { manufacturerDomain }
        assertThat(manufacturersRepo.fetchManufacturers()).isEqualTo(
            Result.success(
                manufacturerDomain
            )
        )
    }

    @Test
    fun returnErrorOnZeroManufacturers()= runTest {
        val manufacturersJson = "{}"
        val manufacturerDomain = emptyList<Manufacturer>()
        coEvery { manufacturersRemoteService.fetchManufacturers() } answers {
            Result.success(JSONObject(manufacturersJson))
        }
        every { jsonToDomainManufacturersMapper.map(any()) } answers { manufacturerDomain }
        val actual =
            isFailureWithMessage(manufacturersRepo.fetchManufacturers(), "No Manufacturer Found")
            assertThat(actual).isTrue()

    }
}
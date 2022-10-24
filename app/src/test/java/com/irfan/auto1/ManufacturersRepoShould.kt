package com.irfan.auto1

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.json.JSONObject
import org.junit.Before
import org.junit.Test

class ManufacturersRepoShould : BaseTest() {


    @RelaxedMockK
    private lateinit var manufacturersRemoteService: ManufacturersRemoteService
    private lateinit var manufacturersRepo: ManufacturersRepo

    @RelaxedMockK
    private lateinit var jsonToDomainManufacturersMapper: JsonToDomainManufacturersMapper

    @Before
    override fun setup() {
        super.setup()
        manufacturersRepo = ManufacturersRepo(manufacturersRemoteService, jsonToDomainManufacturersMapper)
    }

    @Test
    fun notAlterError() {
        val errorMessage = "Any Message"
        every { manufacturersRemoteService.fetchManufacturers() } answers {
            Result.failure(
                Throwable(
                    errorMessage
                )
            )
        }
        val fetchManufacturers = manufacturersRepo.fetchManufacturers()
        isFailureWithMessage(fetchManufacturers, errorMessage)
    }

    @Test
    fun fetchDomainManufacturers() {
        val manufacturersJson = TestDataProvider.getManufacturersAsJsonStrings()
        val manufacturerDomain = TestDataProvider.getManufacturersAsDomainModels()
        every { manufacturersRemoteService.fetchManufacturers() } answers {
            Result.success(
                JSONObject(manufacturersJson)
            )
        }
        every { jsonToDomainManufacturersMapper.map(any()) } answers { manufacturerDomain }
        assertThat(manufacturersRepo.fetchManufacturers()).isEqualTo(Result.success(manufacturerDomain))
    }

    @Test
    fun returnErrorOnZero(){
        val manufacturersJson = "{}"
        val manufacturerDomain = emptyList<Manufacturer>()
        every { manufacturersRemoteService.fetchManufacturers() }answers {
            Result.success(JSONObject(manufacturersJson))
        }
        every { jsonToDomainManufacturersMapper.map(any()) } answers { manufacturerDomain }
        isFailureWithMessage(manufacturersRepo.fetchManufacturers(),"No Manufacturer Found")
    }
}
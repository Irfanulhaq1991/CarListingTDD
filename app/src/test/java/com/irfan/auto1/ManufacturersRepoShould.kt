package com.irfan.auto1

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.stub.StubRepository
import io.mockk.verify
import org.json.JSONObject
import org.junit.Before
import org.junit.Test

class ManufacturersRepoShould : BaseTest() {


    @RelaxedMockK
    private lateinit var manufacturersRemoteService: ManufacturersRemoteService
    private lateinit var manufacturersRepo: ManufacturersRepo

    @RelaxedMockK
    private lateinit var mapper: Mapper<JSONObject, List<Manufacturer>>

    @Before
    override fun setup() {
        super.setup()
        manufacturersRepo = ManufacturersRepo(manufacturersRemoteService, mapper)
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
        every { mapper.map(any()) } answers { manufacturerDomain }
        assertThat(manufacturersRepo.fetchManufacturers()).isEqualTo(Result.success(manufacturerDomain))
    }


}
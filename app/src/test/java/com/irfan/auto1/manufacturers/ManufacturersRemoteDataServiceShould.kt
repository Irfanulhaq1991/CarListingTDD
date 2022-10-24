package com.irfan.auto1.manufacturers

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.*
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class ManufacturersRemoteDataServiceShould : BaseTest() {

    @RelaxedMockK
    private lateinit var pagingManager: PagingManager

    @Before
    override fun setup() {
        super.setup()
    }

    @Test
    fun returnZeroManufacturerJson() = runTest {
        val actual =
            withDataSuccess(
                TestDataProvider.getManufacturersResponseJson("{}"),
                pagingManager
            ).fetchManufacturers().getOrThrow().size

        assertThat(actual)
            .isEqualTo(0)
    }

    @Test
    fun returnOneManufacturerJson() = runTest {
        val actual = withDataSuccess(
            TestDataProvider.getManufacturersResponseJson("{abc:def}"),
            pagingManager
        ).fetchManufacturers()
            .getOrThrow().size
        assertThat(actual)
            .isEqualTo(1)
    }

    @Test
    fun returnManyManufacturerJson() = runTest {
        val actual = withDataSuccess(
            TestDataProvider.getManufacturersResponseJson(),
            pagingManager
        ).fetchManufacturers().getOrThrow().size
        assertThat(actual)
            .isEqualTo(15)
    }


    @Test
    fun returnNoInternetError() = runTest {
        val manufacturer = withNetworkError().fetchManufacturers()
        val actual = isFailureWithMessage(manufacturer, "No Internet")
        assertThat(actual).isTrue()
    }


    @Test
    fun returnErrorOnInvalidJson() = runTest {
        val manufacturer = withDataSuccess("{", pagingManager).fetchManufacturers()
        val actual = isFailureWithMessage(manufacturer, "Error Occurred")
        assertThat(actual).isTrue()
    }

    @Test
    fun executePageManager() = runTest {
        val remoteDataService =
            withDataSuccess(TestDataProvider.getManufacturersResponseJson("{}"), pagingManager)
        remoteDataService.fetchManufacturers()
        coVerify { pagingManager.page() }
        coVerify { pagingManager.setTotalPages(any()) }
        coVerify { pagingManager.pagSize }
    }


    // Methods for contract

    private fun withDataSuccess(
        jsonString: String,
        pagingManager: PagingManager
    ): IManufacturersRemoteDataService {

        val api = object : ManufacturersRemoteAPI {
            override suspend fun getManufacturers(
                nextPage: Int,
                pageSize: Int
            ): Response<ResponseBody> {
                val contentType = "application/json; charset=utf-8".toMediaType()
                return Response.success(jsonString.toResponseBody(contentType))
            }
        }
        return ManufacturersRemoteDataDataService(api, pagingManager)
    }


    private fun withNetworkError(): IManufacturersRemoteDataService {
        val api = object : ManufacturersRemoteAPI {
            override suspend fun getManufacturers(
                nextPage: Int,
                pageSize: Int
            ): Response<ResponseBody> {
                throw IOException()
            }
        }
        return ManufacturersRemoteDataDataService(api, pagingManager)
    }
}
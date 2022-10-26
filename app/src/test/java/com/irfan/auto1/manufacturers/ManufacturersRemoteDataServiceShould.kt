package com.irfan.auto1.manufacturers

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.*
import com.irfan.auto1.manufacturers.data.remote.IManufacturersRemoteDataSource
import com.irfan.auto1.manufacturers.data.remote.ManufacturersRemoteAPI
import com.irfan.auto1.manufacturers.data.remote.ManufacturersRemoteDataDataSource
import com.irfan.auto1.manufacturers.data.remote.PagingManager
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
                TestDataProvider.getResponseJson("{}"),
                pagingManager
            ).fetchManufacturers().getOrThrow().size

        assertThat(actual)
            .isEqualTo(0)
    }

    @Test
    fun returnOneManufacturerJson() = runTest {
        val actual = withDataSuccess(
            TestDataProvider.getResponseJson("{abc:def}"),
            pagingManager
        ).fetchManufacturers()
            .getOrThrow().size
        assertThat(actual)
            .isEqualTo(1)
    }

    @Test
    fun returnManyManufacturerJson() = runTest {
        val actual = withDataSuccess(
            TestDataProvider.getManuFacturerResponseJson(),
            pagingManager
        ).fetchManufacturers().getOrThrow().size
        assertThat(actual)
            .isEqualTo(15)
    }


    @Test
    fun returnNoInternetError() = runTest {
        val manufacturer = withNetworkError().fetchManufacturers()
        val actual = isFailureWithMessage(manufacturer, "Please check your internet connection")
        assertThat(actual).isTrue()
    }


    @Test
    fun returnErrorOnInvalidJson() = runTest {
        val manufacturer = withDataSuccess("{}", pagingManager).fetchManufacturers()
        val actual = isFailureWithMessage(manufacturer, "Request for manufacturers is failed")
        assertThat(actual).isTrue()
    }

    @Test
    fun executePageManager() = runTest {
        val remoteDataService =
            withDataSuccess(TestDataProvider.getResponseJson("{}"), pagingManager)
        remoteDataService.fetchManufacturers()
        coVerify { pagingManager.nextPage() }
        coVerify { pagingManager.setTotalPages(any()) }
        coVerify { pagingManager.pagSize }
    }


    // Methods for contract

    private fun withDataSuccess(
        jsonString: String,
        pagingManager: PagingManager
    ): IManufacturersRemoteDataSource {

        val api = object : ManufacturersRemoteAPI {
            override suspend fun getManufacturers(
                nextPage: Int,
                pageSize: Int
            ): Response<ResponseBody> {
                val contentType = "application/json; charset=utf-8".toMediaType()
                return Response.success(jsonString.toResponseBody(contentType))
            }
        }
        return ManufacturersRemoteDataDataSource(api, pagingManager)
    }


    private fun withNetworkError(): IManufacturersRemoteDataSource {
        val api = object : ManufacturersRemoteAPI {
            override suspend fun getManufacturers(
                nextPage: Int,
                pageSize: Int
            ): Response<ResponseBody> {
                throw IOException()
            }
        }
        return ManufacturersRemoteDataDataSource(api, pagingManager)
    }
}
package com.irfan.auto1.manufacturers

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.*
import com.irfan.auto1.manufacturers.data.remote.*
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
    fun returnErrorOnZero() = runTest {
        val dataSource = withDataSuccess(
                TestDataProvider.getResponseJson("{}"),
                pagingManager
            )

        val actual = isFailureWithMessage(dataSource.doFetching(), "No record found")
        assertThat(actual).isTrue()
    }

    @Test
    fun returnOneManufacturerJson() = runTest {
        val actual = withDataSuccess(
            TestDataProvider.getResponseJson("{abc:def}"),
            pagingManager
        ).doFetching()
            .getOrThrow().size
        assertThat(actual)
            .isEqualTo(1)
    }

    @Test
    fun returnManyManufacturerJson() = runTest {
        val actual = withDataSuccess(
            TestDataProvider.getManuFacturerResponseJson(),
            pagingManager
        ).doFetching().getOrThrow().size
        assertThat(actual)
            .isEqualTo(15)
    }


    @Test
    fun returnNoInternetError() = runTest {
        val manufacturer = withNetworkError().doFetching()
        val actual = isFailureWithMessage(manufacturer, "Please check your internet connection")
        assertThat(actual).isTrue()
    }


    @Test
    fun returnErrorOnInvalidJson() = runTest {
        val manufacturer = withDataSuccess("{}", pagingManager).doFetching()
        val actual = isFailureWithMessage(manufacturer, "Request is failed")
        assertThat(actual).isTrue()
    }

    @Test
    fun executePageManager() = runTest {
        val remoteDataService =
            withDataSuccess(TestDataProvider.getResponseJson("{}"), pagingManager)
        remoteDataService.doFetching()
        coVerify { pagingManager.nextPage() }
        coVerify { pagingManager.setTotalPages(any()) }
        coVerify { pagingManager.pagSize }
    }


    // Methods for contract

    private fun withDataSuccess(
        jsonString: String,
        pagingManager: PagingManager
    ): RemoteDataSource<ManufacturerDto> {

        val api = object : ManufacturersRemoteAPI {
            override suspend fun getManufacturers(
                nextPage: Int,
                pageSize: Int
            ): Response<ResponseBody> {
                val contentType = "application/json; charset=utf-8".toMediaType()
                return Response.success(jsonString.toResponseBody(contentType))
            }
        }
        return ManufacturersRemoteDataSource(api, pagingManager)
    }


    private fun withNetworkError(): RemoteDataSource<ManufacturerDto> {
        val api = object : ManufacturersRemoteAPI {
            override suspend fun getManufacturers(
                nextPage: Int,
                pageSize: Int
            ): Response<ResponseBody> {
                throw IOException()
            }
        }
        return ManufacturersRemoteDataSource(api, pagingManager)
    }
}
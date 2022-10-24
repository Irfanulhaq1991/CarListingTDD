package com.irfan.auto1.manufacturers

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.BaseTest
import com.irfan.auto1.ManufacturersRemoteAPI
import com.irfan.auto1.ManufacturersRemoteService
import com.irfan.auto1.TestDataProvider
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class ManufacturersRemoteServiceShould : BaseTest() {

    @Before
    override fun setup() {
        super.setup()
    }

    @Test
    fun returnZeroManufacturerJson()= runTest {
        val actual = withDataSuccess("{wkda:{}}").fetchManufacturers().getOrThrow().length()
        assertThat(actual)
            .isEqualTo(0)
    }

    @Test
    fun returnOneManufacturerJson() = runTest{

        val actual = withDataSuccess("{wkda:{abc:def}}").fetchManufacturers().getOrThrow().length()
        assertThat(actual)
            .isEqualTo(1)
    }

    @Test
    fun returnManyManufacturerJson() = runTest{
        val actual = withDataSuccess(TestDataProvider.getManufacturersResponseJson()).fetchManufacturers().getOrThrow().length()
        assertThat(actual)
            .isEqualTo(15)
    }


    @Test
    fun returnNoInternetError() = runTest{
        val manufacturer = withNetworkError().fetchManufacturers()
        val actual = isFailureWithMessage(manufacturer, "No Internet")
        assertThat(actual).isTrue()
    }


    @Test
    fun returnErrorOnInvalidJson() = runTest{
        val manufacturer = withDataSuccess("{").fetchManufacturers()
        val actual = isFailureWithMessage(manufacturer, "Error Occurred")
        assertThat(actual).isTrue()
    }


    // Methods for contract

    private fun withDataSuccess(jsonString: String): ManufacturersRemoteService {

        val api =  object : ManufacturersRemoteAPI {
            override suspend fun getManufacturers(): Response<ResponseBody> {
                val contentType = "application/json; charset=utf-8".toMediaType()
                return Response.success(jsonString.toResponseBody(contentType))
            }
        }
        return ManufacturersRemoteService(api)
    }


    private fun withNetworkError(): ManufacturersRemoteService {
        val api = object : ManufacturersRemoteAPI {
            override suspend fun getManufacturers(): Response<ResponseBody> {
                throw IOException()
            }
        }
        return ManufacturersRemoteService(api)
    }
}
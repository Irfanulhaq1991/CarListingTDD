package com.irfan.auto1.year

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.BaseTest
import com.irfan.auto1.TestDataProvider
import com.irfan.auto1.year.data.remote.CarYearsRemoteDataSource
import com.irfan.auto1.year.data.remote.CarYearsRemoteApi
import com.irfan.auto1.common.CarInfo
import com.irfan.auto1.manufacturers.data.remote.RemoteDataSource
import com.irfan.auto1.year.data.remote.CarYearDto

import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class CarYearsRemoteDataSourceShould : BaseTest() {

    @Before
    override fun setup() {
        super.setup()
    }


    @Test
    fun returnErrorOnZero() = runTest {
        val carYearsRemoteDataSource = withData(TestDataProvider.getResponseJson("{}"))
        val errorMessage = "No record found"
        val actual = isFailureWithMessage(carYearsRemoteDataSource.doFetching(CarInfo()), errorMessage)
        assertThat(actual).isTrue()
    }

    @Test
    fun returnManyCarYears() = runTest {
        val carYearsRemoteDataSource = withData(TestDataProvider.getCarYearResponseJson())
        val actual = carYearsRemoteDataSource.doFetching(CarInfo())
        assertThat(actual).isEqualTo(Result.success(TestDataProvider.getYearsAsDto()))
    }

    @Test
    fun returnNoInternetError() = runTest {
        val carYearsRemoteDataSource = withException(IOException())
        val errorMessage = "Please check your internet connection"
        val actual = isFailureWithMessage(carYearsRemoteDataSource.doFetching(
            CarInfo()
        ), errorMessage)
        assertThat(actual).isTrue()
    }

    private fun withData(jsonString: String): RemoteDataSource<CarYearDto> {
        val remoteCarYearsApi = object : CarYearsRemoteApi {
            override suspend fun fetchCarYears(manufacturer:Int, carType:String): Response<ResponseBody> {
                val contentType = "application/json; charset=utf-8".toMediaType()
                return Response.success(jsonString.toResponseBody(contentType))
            }

        }
       return CarYearsRemoteDataSource(remoteCarYearsApi)
    }

    private fun withException(e: Exception): RemoteDataSource<CarYearDto> {
        val remoteCarYearsApi = object : CarYearsRemoteApi {
            override suspend fun fetchCarYears(manufacturer:Int, carType:String): Response<ResponseBody> {
                throw  e
            }

        }
        return CarYearsRemoteDataSource(remoteCarYearsApi)
    }
}
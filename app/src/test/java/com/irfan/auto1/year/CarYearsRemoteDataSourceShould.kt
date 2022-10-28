package com.irfan.auto1.year

import com.irfan.auto1.common.RemoteDataSourceContractTests
import com.irfan.auto1.common.TestDataProvider
import com.irfan.auto1.year.data.remote.CarYearsRemoteDataSource
import com.irfan.auto1.year.data.remote.CarYearsRemoteApi
import com.irfan.auto1.manufacturers.data.remote.RemoteDataSource
import com.irfan.auto1.year.data.remote.CarYearDto

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class CarYearsRemoteDataSourceShould : RemoteDataSourceContractTests<CarYearDto>() {

    override fun withNoData(): RemoteDataSource<CarYearDto> {
        val remoteCarYearsApi = object : CarYearsRemoteApi {
            override suspend fun fetchCarYears(manufacturer:Int, carType:String): Response<ResponseBody> {
                val contentType = "application/json; charset=utf-8".toMediaType()
                return Response.success(TestDataProvider.getResponseJson("{}").toResponseBody(contentType))
            }

        }
        return CarYearsRemoteDataSource(remoteCarYearsApi)
    }

    override fun withData(): RemoteDataSource<CarYearDto> {
        val remoteCarYearsApi = object : CarYearsRemoteApi {
            override suspend fun fetchCarYears(manufacturer:Int, carType:String): Response<ResponseBody> {
                val contentType = "application/json; charset=utf-8".toMediaType()
                return Response.success(TestDataProvider.getCarYearResponseJson().toResponseBody(contentType))
            }

        }
        return CarYearsRemoteDataSource(remoteCarYearsApi)
    }

    override fun withException(e: Exception): RemoteDataSource<CarYearDto> {
        val remoteCarYearsApi = object : CarYearsRemoteApi {
            override suspend fun fetchCarYears(manufacturer:Int, carType:String): Response<ResponseBody> {
                throw  e
            }
        }
        return CarYearsRemoteDataSource(remoteCarYearsApi)
    }
}
package com.irfan.auto1.model.fetch

import com.irfan.auto1.common.RemoteDataSourceContractTests
import com.irfan.auto1.common.TestDataProvider
import com.irfan.auto1.manufacturers.data.remote.RemoteDataSource
import com.irfan.auto1.model.data.remote.ModelDto
import com.irfan.auto1.model.data.remote.ModelRemoteApi
import com.irfan.auto1.model.data.remote.ModelsRemoteDataSource
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class ModelRemoteDataSourceShould : RemoteDataSourceContractTests<ModelDto>() {
    override fun withNoData(): RemoteDataSource<ModelDto> {
        val remoteApi = object : ModelRemoteApi {
            override suspend fun fetchModels(manufacturerId: Int): Response<ResponseBody> {
                val contentType = "application/json; charset=utf-8".toMediaType()
                return Response.success(
                    TestDataProvider.getResponseJson("{}").toResponseBody(contentType)
                )
            }

        }
        return ModelsRemoteDataSource(remoteApi)
    }

    override fun withData(): RemoteDataSource<ModelDto> {
        val remoteApi = object : ModelRemoteApi {
            override suspend fun fetchModels(manufacturerId: Int): Response<ResponseBody> {
                val contentType = "application/json; charset=utf-8".toMediaType()
                return Response.success(
                    TestDataProvider.getModelResponseJson().toResponseBody(contentType)
                )
            }

        }
        return ModelsRemoteDataSource(remoteApi)
    }

    override fun withException(e: Exception): RemoteDataSource<ModelDto> {
        val remoteApi = object : ModelRemoteApi {
            override suspend fun fetchModels(manufacturerId: Int): Response<ResponseBody> {
                throw e
            }

        }
        return ModelsRemoteDataSource(remoteApi)
    }
}
package com.irfan.auto1.model

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.BaseTest
import com.irfan.auto1.TestDataProvider
import com.irfan.auto1.manufacturers.data.remote.model.ManufacturerDto
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class ModelsRemoteDataSourceShould : BaseTest() {

    @Test
    fun returnErrorOnZero() {
        val error = "No Model Found"
        val modelsRemoteDataSource = withData(TestDataProvider.getResponseJson("{}"))
        val actual = isFailureWithMessage(modelsRemoteDataSource.fetchModels(), error)
        assertThat(actual).isTrue()
    }


    @Test
    fun returnMany() {
        val data = TestDataProvider.getModelAsDtos()
        val modelsRemoteDataSource = withData(TestDataProvider.getModelResponseJson())

        val actual = modelsRemoteDataSource.fetchModels().getOrThrow().size
        assertThat(actual).isGreaterThan(1)
    }

    @Test
    fun returnNoInternetError() {
        val modelsRemoteDataSource = withError(IOException())
        val error = "Please Check your Internet Connection"
        val actual = isFailureWithMessage(modelsRemoteDataSource.fetchModels(), error)
        assertThat(actual).isTrue()
    }

    private fun withData(jsonString: String): ModelsRemoteDataSource {
        val remoteApi = object : ModelRemoteApi {
            override fun fetchModels(): Response<ResponseBody> {
                val contentType = "application/json; charset=utf-8".toMediaType()
                return Response.success(jsonString.toResponseBody(contentType))
            }

        }
        return ModelsRemoteDataSource(remoteApi)
    }



    private fun withError(e:Exception): ModelsRemoteDataSource {
        val remoteApi = object : ModelRemoteApi {
            override fun fetchModels(): Response<ResponseBody> {
               throw e
            }

        }
        return ModelsRemoteDataSource(remoteApi)
    }




}
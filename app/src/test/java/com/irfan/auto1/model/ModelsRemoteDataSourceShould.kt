package com.irfan.auto1.model

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.BaseTest
import com.irfan.auto1.TestDataProvider
import com.irfan.auto1.model.data.remote.IModelsRemoteDataSource
import com.irfan.auto1.model.data.remote.ModelRemoteApi
import com.irfan.auto1.model.data.remote.ModelsRemoteDataSource
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class ModelsRemoteDataSourceShould : BaseTest() {

    @Test
    fun returnErrorOnZero() = runTest{
        val error = "No Model Found"
        val modelsRemoteDataSource = withData(TestDataProvider.getResponseJson("{}"))
        val actual = isFailureWithMessage(modelsRemoteDataSource.fetchModels(0), error)
        assertThat(actual).isTrue()
    }


    @Test
    fun returnMany() = runTest{
        val data = TestDataProvider.getModelAsDtos()
        val modelsRemoteDataSource = withData(TestDataProvider.getModelResponseJson())

        val actual = modelsRemoteDataSource.fetchModels(0).getOrThrow().size
        assertThat(actual).isGreaterThan(1)
    }

    @Test
    fun returnNoInternetError()= runTest {
        val modelsRemoteDataSource = withError(IOException())
        val error = "Please Check your Internet Connection"
        val actual = isFailureWithMessage(modelsRemoteDataSource.fetchModels(0), error)
        assertThat(actual).isTrue()
    }

    private fun withData(jsonString: String): IModelsRemoteDataSource {
        val remoteApi = object : ModelRemoteApi {
            override suspend fun fetchModels(manufacturerId:Int): Response<ResponseBody> {
                val contentType = "application/json; charset=utf-8".toMediaType()
                return Response.success(jsonString.toResponseBody(contentType))
            }

        }
        return ModelsRemoteDataSource(remoteApi)
    }



    private fun withError(e:Exception): IModelsRemoteDataSource {
        val remoteApi = object : ModelRemoteApi {
            override suspend fun fetchModels(manufacturerId:Int): Response<ResponseBody> {
               throw e
            }

        }
        return ModelsRemoteDataSource(remoteApi)
    }




}
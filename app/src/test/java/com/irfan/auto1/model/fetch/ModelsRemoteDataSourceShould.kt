package com.irfan.auto1.model.fetch

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.BaseTest
import com.irfan.auto1.TestDataProvider
import com.irfan.auto1.common.CarInfo
import com.irfan.auto1.manufacturers.data.remote.RemoteDataSource
import com.irfan.auto1.model.data.remote.ModelDto
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
        val error = "No record found"
        val modelsRemoteDataSource = withData(TestDataProvider.getResponseJson("{}"))
        val actual = isFailureWithMessage(modelsRemoteDataSource.doFetching(CarInfo()), error)
        assertThat(actual).isTrue()
    }


    @Test
    fun returnMany() = runTest{
        val modelsRemoteDataSource = withData(TestDataProvider.getModelResponseJson())

        val actual = modelsRemoteDataSource.doFetching(CarInfo()).getOrThrow().size
        assertThat(actual).isGreaterThan(1)
    }

    @Test
    fun returnNoInternetError()= runTest {
        val modelsRemoteDataSource = withError(IOException())
        val error = "Please check your internet connection"
        val actual = isFailureWithMessage(modelsRemoteDataSource.doFetching(CarInfo()), error)
        assertThat(actual).isTrue()
    }

    private fun withData(jsonString: String): RemoteDataSource<ModelDto> {
        val remoteApi = object : ModelRemoteApi {
            override suspend fun fetchModels(manufacturerId:Int): Response<ResponseBody> {
                val contentType = "application/json; charset=utf-8".toMediaType()
                return Response.success(jsonString.toResponseBody(contentType))
            }

        }
        return ModelsRemoteDataSource(remoteApi)
    }



    private fun withError(e:Exception): RemoteDataSource<ModelDto> {
        val remoteApi = object : ModelRemoteApi {
            override suspend fun fetchModels(manufacturerId:Int): Response<ResponseBody> {
               throw e
            }

        }
        return ModelsRemoteDataSource(remoteApi)
    }

}
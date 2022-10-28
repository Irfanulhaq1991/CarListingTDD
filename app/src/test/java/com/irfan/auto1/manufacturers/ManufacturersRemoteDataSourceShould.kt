package com.irfan.auto1.manufacturers

import com.irfan.auto1.common.RemoteDataSourceContractTests
import com.irfan.auto1.common.TestDataProvider
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

class ManufacturersRemoteDataSourceShould : RemoteDataSourceContractTests<CarManufacturerDto>() {


    @RelaxedMockK
    private lateinit var pagingManager: PagingManager

    @Before
    override fun setup() {
        super.setup()
    }

    @Test
    fun executePageManager() = runTest {
        val remoteDataService = withData()
        remoteDataService.doFetching()
        coVerify { pagingManager.nextPage() }
        coVerify { pagingManager.setTotalPages(any()) }
        coVerify { pagingManager.pagSize }
        coVerify { pagingManager.updateNextPage() }
    }


    override fun withNoData(): RemoteDataSource<CarManufacturerDto> {
        val api = object : ManufacturersRemoteAPI {
            override suspend fun getManufacturers(
                nextPage: Int,
                pageSize: Int
            ): Response<ResponseBody> {
                val contentType = "application/json; charset=utf-8".toMediaType()
                return Response.success(
                    TestDataProvider.getResponseJson("{}").toResponseBody(contentType)
                )
            }
        }
        return ManufacturersRemoteDataSource(api, pagingManager)
    }

    override fun withData(): RemoteDataSource<CarManufacturerDto> {
        val api = object : ManufacturersRemoteAPI {
            override suspend fun getManufacturers(
                nextPage: Int,
                pageSize: Int
            ): Response<ResponseBody> {
                val contentType = "application/json; charset=utf-8".toMediaType()
                return Response.success(
                    TestDataProvider.getManufacturerResponseJson().toResponseBody(contentType)
                )
            }
        }
        return ManufacturersRemoteDataSource(api, pagingManager)
    }

    override fun withException(e: Exception): RemoteDataSource<CarManufacturerDto> {
        val api = object : ManufacturersRemoteAPI {
            override suspend fun getManufacturers(
                nextPage: Int,
                pageSize: Int
            ): Response<ResponseBody> {
                throw e
            }
        }
        return ManufacturersRemoteDataSource(api, pagingManager)
    }
}
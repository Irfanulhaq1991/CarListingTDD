package com.irfan.auto1.manufactureres.data.remote.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ManufacturersRemoteAPI {
    @GET("manufacturer")
    suspend fun getManufacturers(
        @Query("page") pag: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ResponseBody>
}

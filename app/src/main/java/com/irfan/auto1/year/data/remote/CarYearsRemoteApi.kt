package com.irfan.auto1.year.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CarYearsRemoteApi {

    @GET("built-dates")
    suspend fun fetchCarYears(
        @Query("manufacturer") manufacturer: Int,
        @Query("main-type") mainType: String
    ): Response<ResponseBody>

}

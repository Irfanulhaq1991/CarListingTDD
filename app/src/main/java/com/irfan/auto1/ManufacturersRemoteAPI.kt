package com.irfan.auto1

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface ManufacturersRemoteAPI {
    @GET("##")
    suspend fun getManufacturers(nextPage: Int, pageSize: Int):Response<ResponseBody>
}

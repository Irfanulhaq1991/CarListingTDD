package com.irfan.auto1.model.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ModelRemoteApi {

    @GET("main-types")
    suspend fun fetchModels(@Query("manufacturer") manufacturerId:Int):Response<ResponseBody>
}

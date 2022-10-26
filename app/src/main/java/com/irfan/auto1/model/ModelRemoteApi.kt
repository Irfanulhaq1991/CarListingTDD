package com.irfan.auto1.model

import okhttp3.ResponseBody
import retrofit2.Response

interface ModelRemoteApi {

    fun fetchModels():Response<ResponseBody>
}

package com.irfan.auto1.model.data.remote

import com.irfan.auto1.common.CarInfo
import com.irfan.auto1.manufacturers.data.remote.RemoteDataSource
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

class ModelsRemoteDataSource(private val remoteApi: ModelRemoteApi) : RemoteDataSource<ModelDto>(){

    override suspend fun onFetching(carInfo: CarInfo?): Response<ResponseBody> {
        return remoteApi.fetchModels(carInfo!!.manufacturer.id)
    }

    override fun getDto(key: String, value: String): ModelDto {
       return ModelDto(key,value)
    }
}

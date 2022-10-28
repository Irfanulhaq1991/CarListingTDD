package com.irfan.auto1.model.data.remote

import com.irfan.auto1.common.CarInfo
import com.irfan.auto1.manufacturers.data.remote.RemoteDataSource
import okhttp3.ResponseBody
import retrofit2.Response

class ModelsRemoteDataSource(private val remoteApi: ModelRemoteApi) : RemoteDataSource<CarModelDto>(){

    override suspend fun onFetching(carInfo: CarInfo?): Response<ResponseBody> {
        return remoteApi.fetchModels(carInfo!!.carManufacturer.id)
    }

    override fun getDto(key: String, value: String): CarModelDto {
       return CarModelDto(key,value)
    }
}

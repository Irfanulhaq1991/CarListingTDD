package com.irfan.auto1.year.data.remote

import com.irfan.auto1.common.CarInfo
import com.irfan.auto1.manufacturers.data.remote.RemoteDataSource
import okhttp3.ResponseBody
import retrofit2.Response

class CarYearsRemoteDataSource(private val remoteApi: CarYearsRemoteApi) :
    RemoteDataSource<CarYearDto>() {

    override suspend fun onFetching(carInfo: CarInfo?): Response<ResponseBody> {
        return remoteApi.fetchCarYears(carInfo!!.carManufacturer.id, carInfo.carModel.id)
    }

    override fun getDto(key: String, value: String): CarYearDto {
        return CarYearDto(key, value)
    }
}

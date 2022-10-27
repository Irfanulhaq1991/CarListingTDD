package com.irfan.auto1.year.data.remote

import com.irfan.auto1.common.CarInfo

interface ICarYearsRemoteDataSource {
    suspend fun fetchCarYears(carInfo: CarInfo): Result<List<CarYearDto>>
}
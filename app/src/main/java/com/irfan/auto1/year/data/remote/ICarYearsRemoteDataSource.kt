package com.irfan.auto1.year.data.remote

import com.irfan.auto1.year.domain.model.CarInfo

interface ICarYearsRemoteDataSource {
    suspend fun fetchCarYears(carInfo: CarInfo): Result<List<CarYearDto>>
}
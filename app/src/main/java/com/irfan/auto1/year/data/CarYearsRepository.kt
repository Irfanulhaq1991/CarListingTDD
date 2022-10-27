package com.irfan.auto1.year.data

import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.year.data.remote.CarYearDto
import com.irfan.auto1.year.data.remote.ICarYearsRemoteDataSource
import com.irfan.auto1.year.domain.model.CarYear
import com.irfan.auto1.year.domain.model.CarInfo

class CarYearsRepository(
    private val CarYearsRemoteDataSource: ICarYearsRemoteDataSource,
    private val mapper: IMapper<List<CarYearDto>,List<CarYear>>
) {
    suspend fun fetchCarYears(carInfo: CarInfo): Result<List<CarYear>> {
        return CarYearsRemoteDataSource.fetchCarYears(carInfo).fold({
            Result.success(mapper.map(it))
        }, {
            Result.failure(Throwable(it))
        })
    }

}

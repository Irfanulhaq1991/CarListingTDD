package com.irfan.auto1.year.data

import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.year.data.remote.CarYearDto
import com.irfan.auto1.year.domain.model.CarYear
import com.irfan.auto1.common.CarInfo
import com.irfan.auto1.manufacturers.data.remote.RemoteDataSource

class CarYearsRepository(
    private val CarYearsRemoteDataSource: RemoteDataSource<CarYearDto>,
    private val mapper: IMapper<List<CarYearDto>,List<CarYear>>
) {
    suspend fun fetchCarYears(carInfo: CarInfo): Result<List<CarYear>> {
        return CarYearsRemoteDataSource.doFetching(carInfo).fold({
            Result.success(mapper.map(it))
        }, {
            Result.failure(Throwable(it))
        })
    }

}

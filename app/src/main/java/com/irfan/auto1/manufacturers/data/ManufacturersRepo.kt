package com.irfan.auto1.manufacturers.data

import com.irfan.auto1.manufacturers.data.remote.RemoteDataSource
import com.irfan.auto1.manufacturers.data.remote.CarManufacturerDto
import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.manufacturers.domain.model.CarManufacturer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ManufacturersRepo(
    private val remoteDataSource: RemoteDataSource<CarManufacturerDto>,
    private val dtoToDomainManufacturersMapperCar: IMapper<List<CarManufacturerDto>, List<CarManufacturer>>

) {

    suspend fun fetchManufacturers(): Result<List<CarManufacturer>> = withContext(Dispatchers.IO) {
        remoteDataSource
            .doFetching()
            .fold({
                Result.success(dtoToDomainManufacturersMapperCar.map(it))

            }, { Result.failure(it) })
    }

}

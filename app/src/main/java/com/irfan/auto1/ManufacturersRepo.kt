package com.irfan.auto1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ManufacturersRepo(
    private val manufacturersRemoteDataService: IManufacturersRemoteDataService,
    private val dtoToDomainManufacturersMapper: IMapper<List<ManufacturerDto>, List<Manufacturer>>

) {

    suspend fun fetchManufacturers(): Result<List<Manufacturer>> = withContext(Dispatchers.IO) {
        manufacturersRemoteDataService
            .fetchManufacturers()
            .fold({
                val mappedData = dtoToDomainManufacturersMapper.map(it)

                if (mappedData.isEmpty())
                    Result.failure(Throwable("No Manufacturer Found"))
                else
                    Result.success(dtoToDomainManufacturersMapper.map(it))

            }, { Result.failure(it) })
    }

}

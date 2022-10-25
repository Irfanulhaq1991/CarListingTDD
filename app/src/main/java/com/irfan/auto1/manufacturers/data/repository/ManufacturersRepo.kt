package com.irfan.auto1.manufacturers.data.repository

import com.irfan.auto1.manufacturers.data.remote.datasource.IManufacturersRemoteDataSource
import com.irfan.auto1.manufacturers.data.remote.model.ManufacturerDto
import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.manufacturers.domain.model.Manufacturer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ManufacturersRepo(
    private val manufacturersRemoteDataSource: IManufacturersRemoteDataSource,
    private val dtoToDomainManufacturersMapper: IMapper<List<ManufacturerDto>, List<Manufacturer>>

) {

    suspend fun fetchManufacturers(): Result<List<Manufacturer>> = withContext(Dispatchers.IO) {
        manufacturersRemoteDataSource
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

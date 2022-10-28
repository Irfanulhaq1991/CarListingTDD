package com.irfan.auto1.manufacturers.data

import com.irfan.auto1.manufacturers.data.remote.RemoteDataSource
import com.irfan.auto1.manufacturers.data.remote.ManufacturerDto
import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.manufacturers.domain.model.Manufacturer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ManufacturersRepo(
    private val remoteDataSource: RemoteDataSource<ManufacturerDto>,
    private val dtoToDomainManufacturersMapper: IMapper<List<ManufacturerDto>, List<Manufacturer>>

) {

    suspend fun fetchManufacturers(): Result<List<Manufacturer>> = withContext(Dispatchers.IO) {
        remoteDataSource
            .doFetching()
            .fold({
                Result.success(dtoToDomainManufacturersMapper.map(it))

            }, { Result.failure(it) })
    }

}

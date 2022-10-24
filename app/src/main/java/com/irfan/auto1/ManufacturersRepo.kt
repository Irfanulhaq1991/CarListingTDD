package com.irfan.auto1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class ManufacturersRepo(
    private val manufacturersRemoteService: IManufacturersRemoteService<JSONObject>,
    private val jsonToDomainManufacturersMapper: IMapper<JSONObject, List<Manufacturer>>

) {

    suspend fun fetchManufacturers(): Result<List<Manufacturer>> = withContext(Dispatchers.IO) {
        manufacturersRemoteService
            .fetchManufacturers()
            .fold({
                val mappedData = jsonToDomainManufacturersMapper.map(it)

                if (mappedData.isEmpty())
                    Result.failure(Throwable("No Manufacturer Found"))
                else
                    Result.success(jsonToDomainManufacturersMapper.map(it))

            }, { Result.failure(it) })
    }

}

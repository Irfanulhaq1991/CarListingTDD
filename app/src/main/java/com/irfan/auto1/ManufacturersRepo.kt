package com.irfan.auto1

import org.json.JSONObject

class ManufacturersRepo(
    private val manufacturersRemoteService: ManufacturersRemoteService,
    private val jsonToDomainManufacturersMapper: IMapper<JSONObject, List<Manufacturer>>

) {

    fun fetchManufacturers(): Result<List<Manufacturer>> {
        return manufacturersRemoteService
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

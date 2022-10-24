package com.irfan.auto1

class ManufacturersRepo(
    private val manufacturersRemoteService: ManufacturersRemoteService,
    private val jsonToDomainManufacturersMapper:JsonToDomainManufacturersMapper

    ) {

    fun fetchManufacturers(): Result<List<Manufacturer>> {

        return manufacturersRemoteService
            .fetchManufacturers()
            .fold({ Result.success(jsonToDomainManufacturersMapper.map(it)) }, { Result.failure(it) })
    }

}

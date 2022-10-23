package com.irfan.auto1

import org.json.JSONObject

class ManufacturersRepo(
    private val manufacturersRemoteService: ManufacturersRemoteService,
    private val mapper:Mapper<JSONObject,List<Manufacturer>>

    ) {

    fun fetchManufacturers(): Result<List<Manufacturer>> {

        return manufacturersRemoteService
            .fetchManufacturers()
            .fold({ Result.success(mapper.map(it)) }, { Result.failure(it) })
    }

}

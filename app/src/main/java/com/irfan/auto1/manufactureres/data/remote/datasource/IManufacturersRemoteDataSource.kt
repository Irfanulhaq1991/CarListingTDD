package com.irfan.auto1.manufactureres.data.remote.datasource

import com.irfan.auto1.manufactureres.data.remote.model.ManufacturerDto


interface IManufacturersRemoteDataSource {
    suspend fun fetchManufacturers(): Result<List<ManufacturerDto>>
}
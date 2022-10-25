package com.irfan.auto1.manufacturers.data.remote.datasource

import com.irfan.auto1.manufacturers.data.remote.model.ManufacturerDto


interface IManufacturersRemoteDataSource {
    suspend fun fetchManufacturers(): Result<List<ManufacturerDto>>
}
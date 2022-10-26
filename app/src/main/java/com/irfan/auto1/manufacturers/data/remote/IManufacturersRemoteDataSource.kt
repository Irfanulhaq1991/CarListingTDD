package com.irfan.auto1.manufacturers.data.remote

import com.irfan.auto1.manufacturers.data.remote.ManufacturerDto


interface IManufacturersRemoteDataSource {
    suspend fun fetchManufacturers(): Result<List<ManufacturerDto>>
}
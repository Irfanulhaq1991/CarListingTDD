package com.irfan.auto1


interface IManufacturersRemoteDataService {
    suspend fun fetchManufacturers(): Result<List<ManufacturerDto>>
}
package com.irfan.auto1


interface IManufacturersRemoteService<out O> {
    suspend fun fetchManufacturers(): Result<O>
}
package com.irfan.auto1.model.data.remote

import com.irfan.auto1.common.CarInfo

interface IModelsRemoteDataSource {
    suspend fun fetchModels(manufacturerId: CarInfo): Result<List<ModelDto>>
}
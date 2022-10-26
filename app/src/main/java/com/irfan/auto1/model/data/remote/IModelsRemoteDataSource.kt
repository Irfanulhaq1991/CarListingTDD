package com.irfan.auto1.model.data.remote

interface IModelsRemoteDataSource {
    suspend fun fetchModels(manufacturerId:Int): Result<List<ModelDto>>
}
package com.irfan.auto1.model.data

import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.model.data.remote.IModelsRemoteDataSource
import com.irfan.auto1.model.data.remote.ModelDto
import com.irfan.auto1.model.domain.model.Model
import kotlinx.coroutines.Dispatchers

class ModelsRepository(
    private val mapper: IMapper<List<ModelDto>, List<Model>>,
    private val modelsRemoteDataSource: IModelsRemoteDataSource
) {
    suspend fun fetchModels(manufacturerId:Int): Result<List<Model>> = with(Dispatchers.IO) {
       return modelsRemoteDataSource.fetchModels(manufacturerId).fold({ Result.success(mapper.map(it))},{Result.failure(it)})
    }

}

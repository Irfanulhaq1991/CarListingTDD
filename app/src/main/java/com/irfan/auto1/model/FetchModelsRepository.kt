package com.irfan.auto1.model

import com.irfan.auto1.manufacturers.domain.mapper.IMapper

class FetchModelsRepository(
    private val mapper: IMapper<List<ModelDto>, List<Model>>,
    private val modelsRemoteDataSource: ModelsRemoteDataSource
) {
    fun fetchModels(): Result<List<Model>> {
       return modelsRemoteDataSource.fetchModels().fold({ Result.success(mapper.map(it))},{Result.failure(it)})
    }

}

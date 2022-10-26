package com.irfan.auto1.model.data

import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.model.AppCache
import com.irfan.auto1.model.IAppCache
import com.irfan.auto1.model.data.remote.IModelsRemoteDataSource
import com.irfan.auto1.model.data.remote.ModelDto
import com.irfan.auto1.model.domain.model.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

const val MODEL_KEY = "modelKey"

class ModelsRepository(
    private val mapper: IMapper<List<ModelDto>, List<Model>>,
    private val modelsRemoteDataSource: IModelsRemoteDataSource,
    private val cache: IAppCache<String, List<Model>>
) {
    suspend fun fetchModels(manufacturerId: Int): Result<List<Model>> = with(Dispatchers.IO) {
        return modelsRemoteDataSource
            .fetchModels(manufacturerId)
            .fold(
                {
                    Result.success(
                        mapper
                            .map(it)
                            .apply { cache.put(MODEL_KEY, this) }

                    )
                },
                {
                    Result.failure(it)
                }
            )

    }

}

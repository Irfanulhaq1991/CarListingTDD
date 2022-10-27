package com.irfan.auto1.model.data

import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.model.data.remote.IModelsRemoteDataSource
import com.irfan.auto1.model.data.remote.ModelDto
import com.irfan.auto1.model.domain.model.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val MODEL_KEY = "modelKey"

class ModelsRepository(
    private val mapper: IMapper<List<ModelDto>, List<Model>>,
    private val modelsRemoteDataSource: IModelsRemoteDataSource,
    private val modelFilter: BaseModelFilter
) {
    suspend fun fetchModels(manufacturerId: Int): Result<List<Model>> =
        withContext(Dispatchers.IO) {
            modelsRemoteDataSource
                .fetchModels(manufacturerId)
                .fold(
                    {
                        Result.success(
                            mapper
                                .map(it)
                                .apply { modelFilter.setSearchData(this) }
                        )
                    },
                    {
                        Result.failure(it)
                    }
                )

        }

    suspend fun search(query: String): Result<List<Model>> = withContext(Dispatchers.Default) {
        val filteredResult = modelFilter.filter(query)
        Result.success(filteredResult)
    }

}

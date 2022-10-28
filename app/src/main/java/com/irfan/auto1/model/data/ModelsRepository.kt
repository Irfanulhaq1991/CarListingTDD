package com.irfan.auto1.model.data

import com.irfan.auto1.common.CarInfo
import com.irfan.auto1.manufacturers.data.remote.RemoteDataSource
import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.model.data.remote.CarModelDto
import com.irfan.auto1.model.domain.model.CarModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val MODEL_KEY = "modelKey"

class ModelsRepository(
    private val mapper: IMapper<List<CarModelDto>, List<CarModel>>,
    private val modelsRemoteDataSource: RemoteDataSource<CarModelDto>,
    private val modelFilter: BaseModelFilter
) {
    suspend fun fetchModels(carInfo: CarInfo): Result<List<CarModel>> =
        withContext(Dispatchers.IO) {
            modelsRemoteDataSource
                .doFetching(carInfo)
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

    suspend fun search(query: String): Result<List<CarModel>> = withContext(Dispatchers.Default) {
        val filteredResult = modelFilter.filter(query)
        Result.success(filteredResult)
    }

}

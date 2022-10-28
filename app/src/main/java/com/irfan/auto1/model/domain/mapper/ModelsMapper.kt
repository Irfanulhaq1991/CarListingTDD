package com.irfan.auto1.model.domain.mapper

import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.model.data.remote.CarModelDto
import com.irfan.auto1.model.domain.model.CarModel

class ModelsMapper:IMapper<List<CarModelDto>,List<CarModel>> {
    override suspend fun map(input: List<CarModelDto>): List<CarModel> {
        return input.map { CarModel(it.modelId?:"",it.modelName?:"") }
    }
}
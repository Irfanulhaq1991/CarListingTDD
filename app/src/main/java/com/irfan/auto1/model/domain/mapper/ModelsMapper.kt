package com.irfan.auto1.model.domain.mapper

import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.model.data.remote.ModelDto
import com.irfan.auto1.model.domain.model.Model

class ModelsMapper:IMapper<List<ModelDto>,List<Model>> {
    override suspend fun map(input: List<ModelDto>): List<Model> {
        return input.map { Model(it.modelId?:"",it.modelName?:"") }
    }
}
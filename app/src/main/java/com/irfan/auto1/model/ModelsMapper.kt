package com.irfan.auto1.model

import com.irfan.auto1.manufacturers.domain.mapper.IMapper

class ModelsMapper:IMapper<List<ModelDto>,List<Model>> {
    override fun map(input: List<ModelDto>): List<Model> {
        return input.map { Model(it.modelId?:"",it.modelName?:"") }
    }
}
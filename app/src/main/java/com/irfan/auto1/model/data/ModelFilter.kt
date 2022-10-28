package com.irfan.auto1.model.data

import com.irfan.auto1.model.domain.model.CarModel


class ModelFilter : BaseModelFilter() {
    override fun filter(query: String): List<CarModel> {
        return data.filter {
            it.name.lowercase().contains(query.lowercase())
        }
    }
}

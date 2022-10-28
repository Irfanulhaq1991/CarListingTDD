package com.irfan.auto1.model.data

import com.irfan.auto1.model.domain.model.CarModel

abstract class BaseModelFilter {
    protected var data = listOf<CarModel>()
    abstract fun filter(query: String): List<CarModel>
    fun setSearchData(data: List<CarModel>) {
        this.data = data
    }
}
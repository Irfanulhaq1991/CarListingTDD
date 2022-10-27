package com.irfan.auto1.model.data

import com.irfan.auto1.model.domain.model.Model
import kotlin.math.abs

abstract class BaseModelFilter {
    var data = listOf<Model>()
    abstract fun filter(query: String): List<Model>
    fun setSearchData(data: List<Model>) {
        this.data = data
    }
}
package com.irfan.auto1.year.domain.mapper

import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.year.data.remote.CarYearDto
import com.irfan.auto1.year.domain.model.CarYear

class CarYearsMapper:IMapper<List<CarYearDto>,List<CarYear>> {
    override suspend fun map(input: List<CarYearDto>): List<CarYear> {
        return input.map { CarYear(it.id.toInt(),it.name?:"") }
    }

}

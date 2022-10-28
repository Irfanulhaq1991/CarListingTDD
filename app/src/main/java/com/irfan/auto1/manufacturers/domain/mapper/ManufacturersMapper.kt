package com.irfan.auto1.manufacturers.domain.mapper

import com.irfan.auto1.manufacturers.data.remote.CarManufacturerDto
import com.irfan.auto1.manufacturers.domain.model.CarManufacturer


class ManufacturersMapper : IMapper<List<CarManufacturerDto>, List<CarManufacturer>> {
    override suspend fun map(input: List<CarManufacturerDto>): List<CarManufacturer> {
        return input.map { CarManufacturer(it.manufacturerId.toInt(), it.manufacturerName) }
    }

}

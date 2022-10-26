package com.irfan.auto1.manufacturers.domain.mapper

import com.irfan.auto1.manufacturers.data.remote.ManufacturerDto
import com.irfan.auto1.manufacturers.domain.model.Manufacturer


class ManufacturersMapper : IMapper<List<ManufacturerDto>, List<Manufacturer>> {
    override suspend fun map(input: List<ManufacturerDto>): List<Manufacturer> {
        return input.map { Manufacturer(it.manufacturerId.toInt(), it.manufacturerName) }
    }

}

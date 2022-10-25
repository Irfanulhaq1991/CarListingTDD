package com.irfan.auto1.manufacturers.domain.mapper

import com.irfan.auto1.manufacturers.data.remote.model.ManufacturerDto
import com.irfan.auto1.manufacturers.domain.model.Manufacturer


class DtoToDomainManufacturersMapper : IMapper<List<ManufacturerDto>, List<Manufacturer>> {
    override fun map(input: List<ManufacturerDto>): List<Manufacturer> {
        return input.map { Manufacturer(it.manufacturerId.toInt(), it.manufacturerName) }
    }

}

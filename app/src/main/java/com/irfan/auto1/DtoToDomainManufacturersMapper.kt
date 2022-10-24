package com.irfan.auto1


class DtoToDomainManufacturersMapper : IMapper<List<ManufacturerDto>, List<Manufacturer>> {
    override fun map(input: List<ManufacturerDto>): List<Manufacturer> {
        return input.map { Manufacturer(it.manufacturerId.toInt(), it.manufacturerName) }
    }

}

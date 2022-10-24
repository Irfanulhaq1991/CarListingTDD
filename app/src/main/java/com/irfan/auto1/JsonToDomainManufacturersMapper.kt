package com.irfan.auto1

import org.json.JSONObject


class JsonToDomainManufacturersMapper : IMapper<JSONObject, List<Manufacturer>> {
    override fun map(input: JSONObject): List<Manufacturer> {
        val manufacturers = mutableListOf<Manufacturer>()
        val keys = input.keys()

        keys.forEach {
            manufacturers
                .add(Manufacturer(it.toInt(), input.getString(it)))
        }

        return manufacturers
    }


}

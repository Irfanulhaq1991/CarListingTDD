package com.irfan.auto1

object TestDataProvider {
    fun getManufacturersAsJsonStrings(): String {
        return """{
            "107": "Bentley",
            "125": "Borgward",
            "130": "BMW",
            "141": "Buick",
            "145": "Brilliance",
            "150": "Cadillac",
            "157": "Caterham",
            "160": "Chevrolet",
        }""".trim()
    }

    fun getManufacturersAsDomainModels(): List<Manufacturer> {
        return listOf(
            Manufacturer(107, "Bentley"),
            Manufacturer(125, "Borgward"),
            Manufacturer(130, "BMW"),
            Manufacturer(141, "Buick"),
            Manufacturer(145, "Brilliance"),
            Manufacturer(150, "Cadillac"),
            Manufacturer(157, "Caterham"),
            Manufacturer(160, "Chevrolet"),
        )
    }
}
package com.irfan.auto1

import com.irfan.auto1.manufacturers.domain.model.Manufacturer
import com.irfan.auto1.manufacturers.data.remote.model.ManufacturerDto

object TestDataProvider {
  
    fun getManufacturersResponseJson(): String {
        return """{
      "page": 0,
      "pageSize": 15,
      "totalPageCount": 6,
      "wkda": {
        "107": "Bentley",
        "125": "Borgward",
        "130": "BMW",
        "141": "Buick",
        "145": "Brilliance",
        "150": "Cadillac",
        "157": "Caterham",
        "160": "Chevrolet",
        "020": "Abarth",
        "040": "Alfa Romeo",
        "042": "Alpina",
        "043": "Alpine",
        "057": "Aston Martin",
        "060": "Audi",
        "095": "Barkas"
      }
    }""".trim()
    }

    fun getManufacturersResponseJson(jsonObj:String): String {
        return """{
      "page": 0,
      "pageSize": 15,
      "totalPageCount": 6,
      "wkda": $jsonObj
    }""".trim()
    }

    fun getManufacturersAsDto(): List<ManufacturerDto> {
       return listOf(
           ManufacturerDto("60", "Audi"),
            ManufacturerDto("160", "Chevrolet"),
            ManufacturerDto("150", "Cadillac"),
            ManufacturerDto("40", "Alfa Romeo"),
            ManufacturerDto("95", "Barkas"),
            ManufacturerDto("130", "BMW"),
            ManufacturerDto("141", "Buick"),
            ManufacturerDto("20", "Abarth"),
            ManufacturerDto("42", "Alpina"),
            ManufacturerDto("43", "Alpine"),
            ManufacturerDto("145", "Brilliance"),
            ManufacturerDto("57", "Aston Martin"),
            ManufacturerDto("157", "Caterham"),
            ManufacturerDto("125", "Borgward"),
            ManufacturerDto("107", "Bentley")
       )
    }

    fun getManufacturersAsDomainModels(): List<Manufacturer> {

        return listOf(
            Manufacturer(60, "Audi"),
            Manufacturer(160, "Chevrolet"),
            Manufacturer(150, "Cadillac"),
            Manufacturer(40, "Alfa Romeo"),
            Manufacturer(95, "Barkas"),
            Manufacturer(130, "BMW"),
            Manufacturer(141, "Buick"),
            Manufacturer(20, "Abarth"),
            Manufacturer(42, "Alpina"),
            Manufacturer(43, "Alpine"),
            Manufacturer(145, "Brilliance"),
            Manufacturer(57, "Aston Martin"),
            Manufacturer(157, "Caterham"),
            Manufacturer(125, "Borgward"),
            Manufacturer(107, "Bentley")
        )
    }
}
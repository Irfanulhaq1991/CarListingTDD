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
            "020": "Abarth",
            "040": "Alfa Romeo",
            "042": "Alpina",
            "043": "Alpine",
            "057": "Aston Martin",
            "060": "Audi",
            "095": "Barkas"
        }""".trim()
    }

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

    fun getManufacturersAsDomainModels(): List<Manufacturer> {

        return listOf(Manufacturer(60, name="Audi"),
            Manufacturer(160, name="Chevrolet"),
            Manufacturer(150, "Cadillac"),
            Manufacturer(40, name="Alfa Romeo"),
            Manufacturer(95, name="Barkas"),
            Manufacturer(130, name="BMW"),
            Manufacturer(141, name="Buick"),
            Manufacturer(20, name="Abarth"),
            Manufacturer(42, name="Alpina"),
            Manufacturer(43, name="Alpine"),
            Manufacturer(145, name="Brilliance"),
            Manufacturer(57, name="Aston Martin"),
            Manufacturer(157, name="Caterham"),
            Manufacturer(125, name="Borgward"),
            Manufacturer(107, name="Bentley"))
//        return listOf(
//            Manufacturer(107, "Bentley"),
//            Manufacturer(125, "Borgward"),
//            Manufacturer(130, "BMW"),
//            Manufacturer(141, "Buick"),
//            Manufacturer(145, "Brilliance"),
//            Manufacturer(150, "Cadillac"),
//            Manufacturer(157, "Caterham"),
//            Manufacturer(160, "Chevrolet"),
//        )
    }
}
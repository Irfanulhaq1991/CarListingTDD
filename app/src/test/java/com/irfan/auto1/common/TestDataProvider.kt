package com.irfan.auto1.common

import com.irfan.auto1.manufacturers.domain.model.CarManufacturer
import com.irfan.auto1.manufacturers.data.remote.CarManufacturerDto
import com.irfan.auto1.model.domain.model.CarModel
import com.irfan.auto1.model.data.remote.CarModelDto
import com.irfan.auto1.year.domain.model.CarYear
import com.irfan.auto1.year.data.remote.CarYearDto

object TestDataProvider {
    fun getResponseJson(jsonObj: String): String {
        return """{
      "page": 0,
      "pageSize": 15,
      "totalPageCount": 6,
      "wkda": $jsonObj
    }""".trim()
    }


    //manufacturer data
    fun getManufacturerResponseJson(): String {
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


    fun getManufacturersAsDto(): List<CarManufacturerDto> {
        return listOf(
            CarManufacturerDto("60", "Audi"),
            CarManufacturerDto("160", "Chevrolet"),
            CarManufacturerDto("150", "Cadillac"),
            CarManufacturerDto("40", "Alfa Romeo"),
            CarManufacturerDto("95", "Barkas"),
            CarManufacturerDto("130", "BMW"),
            CarManufacturerDto("141", "Buick"),
            CarManufacturerDto("20", "Abarth"),
            CarManufacturerDto("42", "Alpina"),
            CarManufacturerDto("43", "Alpine"),
            CarManufacturerDto("145", "Brilliance"),
            CarManufacturerDto("57", "Aston Martin"),
            CarManufacturerDto("157", "Caterham"),
            CarManufacturerDto("125", "Borgward"),
            CarManufacturerDto("107", "Bentley")
        )
    }

    fun getManufacturersAsDomainModels(): List<CarManufacturer> {

        return listOf(
            CarManufacturer(60, "Audi"),
            CarManufacturer(160, "Chevrolet"),
            CarManufacturer(150, "Cadillac"),
            CarManufacturer(40, "Alfa Romeo"),
            CarManufacturer(95, "Barkas"),
            CarManufacturer(130, "BMW"),
            CarManufacturer(141, "Buick"),
            CarManufacturer(20, "Abarth"),
            CarManufacturer(42, "Alpina"),
            CarManufacturer(43, "Alpine"),
            CarManufacturer(145, "Brilliance"),
            CarManufacturer(57, "Aston Martin"),
            CarManufacturer(157, "Caterham"),
            CarManufacturer(125, "Borgward"),
            CarManufacturer(107, "Bentley")
        )
    }

    // Model data

    fun getModelResponseJson(): String {
        return """{
          "page": 0,
          "pageSize": 2147483647,
          "totalPageCount": 1,
          "wkda": {
            "1er": "1er",
            "2er": "2er",
            "3er": "3er",
            "4er": "4er",
            "5er": "5er",
            "6er": "6er",
            "7er": "7er",
            "8er": "8er",
            "i3": "i3",
            "i8": "i8",
            "X1": "X1",
            "X2": "X2",
            "X3": "X3",
            "X4": "X4",
            "X5": "X5",
            "X6": "X6",
            "X7": "X7",
            "Z1": "Z1",
            "Z3": "Z3",
            "Z4": "Z4",
            "Z8": "Z8"
          }
        }""".trim()
    }


    fun getModelAsDomainModels(): List<CarModel> {

        return listOf(
            CarModel("3er", "3er"),
            CarModel("4er", "4er"),
            CarModel("5er", "5er"),
            CarModel("6er", "6er"),
            CarModel("7er", "7er"),
            CarModel("8er", "8er"),
            CarModel("i3", "i3"),
            CarModel("i8", "i8"),
            CarModel("Z1", "Z1"),
            CarModel("X1", "X1"),
            CarModel("Z3", "Z3"),
            CarModel("X2", "X2"),
            CarModel("Z4", "Z4"),
            CarModel("X3", "X3"),
            CarModel("X4", "X4"),
            CarModel("X5", "X5"),
            CarModel("1er", "1er"),
            CarModel("X6", "X6"),
            CarModel("Z8", "Z8"),
            CarModel("2er", "2er"),
            CarModel("X7", "X7")
        )


    }

    fun getModelAsDtos(): List<CarModelDto> {

        return listOf(
            CarModelDto("1er", "1er"),
            CarModelDto("2er", "2er"),
            CarModelDto("3er", "3er"),
            CarModelDto("4er", "4er"),
            CarModelDto("5er", "5er"),
            CarModelDto("6er", "6er"),
            CarModelDto("8er", "8er"),
            CarModelDto("i3", "i3"),
            CarModelDto("i8", "i8"),
            CarModelDto("X1", "X1"),
            CarModelDto("X2", "X2"),
            CarModelDto("X3", "X3"),
            CarModelDto("X4", "X4"),
            CarModelDto("X5", "X5"),
            CarModelDto("X6", "X6"),
            CarModelDto("X7", "X7"),
            CarModelDto("Z1", "Z1"),
            CarModelDto("Z2", "Z3"),
            CarModelDto("Z4", "Z4"),
            CarModelDto("Z8", "Z8")
        )
    }

    // Year

    fun getCarYearResponseJson(): String {
        return """{
  "wkda": {
            "2014": "2014",
            "2015": "2015",
            "2016": "2016",
            "2017": "2017",
            "2018": "2018",
            "2019": "2019"
        }
        }""".trim()
    }


    fun getYearsAsDomainModels(): List<CarYear> {

        return listOf(
            CarYear(2019, "2019"),
            CarYear(2018, "2018"),
            CarYear(2017, "2017"),
            CarYear(2016, "2016"),
            CarYear(2015, "2015"),
            CarYear(2014, "2014")

        )
    }


    fun getYearsAsDto(): List<CarYearDto> {

        return listOf(
            CarYearDto("2019", "2019"),
            CarYearDto("2018", "2018"),
            CarYearDto("2017", "2017"),
            CarYearDto("2016", "2016"),
            CarYearDto("2015", "2015"),
            CarYearDto("2014", "2014")
        )
    }
}
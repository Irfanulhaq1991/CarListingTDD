package com.irfan.auto1.common

import com.irfan.auto1.manufacturers.domain.model.CarManufacturer
import com.irfan.auto1.model.domain.model.CarModel
import com.irfan.auto1.year.domain.model.CarYear
import java.io.Serializable

data class CarInfo(val carManufacturer:CarManufacturer= CarManufacturer(),
                   val carModel:CarModel=CarModel(),
                   val year: CarYear = CarYear()
):Serializable

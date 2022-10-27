package com.irfan.auto1.year.domain.model

import com.irfan.auto1.manufacturers.domain.model.Manufacturer
import com.irfan.auto1.model.domain.model.Model
import java.io.Serializable

data class CarInfo(val manufacturer:Manufacturer= Manufacturer(),
                   val model:Model=Model(),
                   val year:CarYear=CarYear()
):Serializable

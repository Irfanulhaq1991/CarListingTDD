package com.irfan.auto1.manufacturers.domain.model

import com.irfan.auto1.common.BaseModel
import java.io.Serializable
// Domain Entity
data class CarManufacturer(val id:Int = 0, override val name:String = ""): BaseModel,Serializable

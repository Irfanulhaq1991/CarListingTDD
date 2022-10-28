package com.irfan.auto1.model.domain.model

import com.irfan.auto1.common.BaseModel
import java.io.Serializable

data class CarModel(val id: String = "", override val name: String = "") : BaseModel, Serializable

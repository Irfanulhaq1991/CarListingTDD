package com.irfan.auto1.year.domain.model

import com.irfan.auto1.common.BaseModel
import java.io.Serializable

data class CarYear(val id: Int = 0, override val name: String = "") : BaseModel, Serializable

package com.irfan.auto1

import com.irfan.auto1.model.domain.model.Model
import com.irfan.auto1.year.CarYear

data class CarYearsUiState(
    val carYears: List<CarYear> = emptyList(),
    val errorMessage: String? = null,
    val isError:Boolean = false,
    val loading: Boolean = false,
    val update: Boolean = false
)
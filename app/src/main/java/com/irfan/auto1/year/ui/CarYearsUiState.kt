package com.irfan.auto1.year.ui

import com.irfan.auto1.year.domain.model.CarYear

data class CarYearsUiState(
    val carYears: List<CarYear> = emptyList(),
    val errorMessage: String? = null,
    val isError:Boolean = false,
    val loading: Boolean = false,
    val update: Boolean = false
)
package com.irfan.auto1.manufacturers.ui

import com.irfan.auto1.manufacturers.domain.model.Manufacturer

data class ManufacturerUiState(
    val manufacturers: List<Manufacturer> = emptyList(),
    val errorMessage: String? = null,
    val isError:Boolean = false,
    val loading: Boolean = false,
)

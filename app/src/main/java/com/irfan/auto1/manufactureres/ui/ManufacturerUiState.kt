package com.irfan.auto1.manufactureres.ui

import com.irfan.auto1.manufactureres.domain.model.Manufacturer

data class ManufacturerUiState(
    val manufacturers: List<Manufacturer> = emptyList(),
    val errorMessage: String? = null,
    val showLoading: Boolean = false,
)

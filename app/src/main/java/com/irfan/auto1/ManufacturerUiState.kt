package com.irfan.auto1

data class ManufacturerUiState(
    val manufacturers: List<Manufacturer> = emptyList(),
    val errorMessage: String? = null,
    val showLoading: Boolean = false,
)

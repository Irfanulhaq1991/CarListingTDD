package com.irfan.auto1.model

import com.irfan.auto1.manufacturers.domain.model.Manufacturer

class ModelUiState(
    val manufacturers: List<Model> = emptyList(),
    val errorMessage: String? = null,
    val isError:Boolean = false,
    val loading: Boolean = false,
)

package com.irfan.auto1.model.ui

import com.irfan.auto1.model.domain.model.Model

data class ModelUiState(
    val models: List<Model> = emptyList(),
    val errorMessage: String? = null,
    val isError:Boolean = false,
    val loading: Boolean = false,
    val update: Boolean = false
)

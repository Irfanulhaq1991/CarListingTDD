package com.irfan.auto1.model.ui

import com.irfan.auto1.common.BaseUIState
import com.irfan.auto1.model.domain.model.Model

data class ModelUiState(
    override val data: List<Model> = emptyList(),
    override val errorMessage: String? = null,
    override val isError:Boolean = false,
    override val loading: Boolean = false,
    override val update: Boolean = false
):BaseUIState<Model>

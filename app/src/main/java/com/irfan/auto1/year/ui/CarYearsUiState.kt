package com.irfan.auto1.year.ui

import com.irfan.auto1.common.BaseUIState
import com.irfan.auto1.year.domain.model.CarYear

data class CarYearsUiState(
    override val data: List<CarYear> = emptyList(),
    override val errorMessage: String? = null,
    override val isError: Boolean = false,
    override val loading: Boolean = false,
    override val update: Boolean = false
) : BaseUIState<CarYear>
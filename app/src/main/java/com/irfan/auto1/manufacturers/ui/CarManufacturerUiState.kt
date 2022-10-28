package com.irfan.auto1.manufacturers.ui

import com.irfan.auto1.common.BaseUIState
import com.irfan.auto1.manufacturers.domain.model.CarManufacturer

data class CarManufacturerUiState(
    override val data: List<CarManufacturer> = emptyList(),
    override val errorMessage: String? = null,
    override val isError: Boolean = false,
    override val loading: Boolean = false,
    override val update: Boolean = false
) : BaseUIState<CarManufacturer>

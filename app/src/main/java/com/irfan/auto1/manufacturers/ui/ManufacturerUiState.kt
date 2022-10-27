package com.irfan.auto1.manufacturers.ui

import com.irfan.auto1.common.BaseUIState
import com.irfan.auto1.manufacturers.domain.model.Manufacturer

data class ManufacturerUiState(
    override val data: List<Manufacturer> = emptyList(),
    override val errorMessage: String? = null,
    override val isError: Boolean = false,
    override val loading: Boolean = false,
    override val update: Boolean = false
) : BaseUIState<Manufacturer>

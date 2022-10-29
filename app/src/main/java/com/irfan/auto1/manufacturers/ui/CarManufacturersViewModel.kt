package com.irfan.auto1.manufacturers.ui

import androidx.lifecycle.viewModelScope
import com.irfan.auto1.common.BaseViewModel
import com.irfan.auto1.manufacturers.FetchManufacturersUseCase
import com.irfan.auto1.manufacturers.domain.model.CarManufacturer
import kotlinx.coroutines.launch

class CarManufacturersViewModel(private val fetchManufacturersUseCase: FetchManufacturersUseCase) :
    BaseViewModel<CarManufacturer, CarManufacturerUiState, Any>() {
    private var shouldRestoreOdlState = false

    //Backing Property
    private val _data = mutableListOf<CarManufacturer>()

    fun fetchManufacturers() {
        if (!shouldRestoreOdlState)
            doFetching()
        shouldRestoreOdlState = false
    }

    override fun onFetch(param: Any?) {
        viewModelScope.launch {
            fetchManufacturersUseCase().run {
                reduceState(this)
            }
        }
    }

    override fun onSuccess(result: List<CarManufacturer>, stateCar: CarManufacturerUiState?) {
        val newState = (stateCar ?: CarManufacturerUiState())
            .copy(
                data = _data.apply { addAll(result) },
                loading = false,
                isError = false,
            )
        update(newState)
    }

    override fun onError(errorMessage: String, stateCar: CarManufacturerUiState?) {
        val newState = (stateCar ?: CarManufacturerUiState())
            .copy(
                errorMessage = errorMessage,
                isError = true,
                loading = false
            )
        update(newState)
    }

    override fun onLoading(state: CarManufacturerUiState?) {
        val newState = (state ?: CarManufacturerUiState()).copy(
            loading = true,
            isError = false
        )
        update(newState)
    }

    override fun onRendered(state: CarManufacturerUiState) {

        val newState = state
            .copy(errorMessage = null, update = false)
        update(newState)
    }

    private fun update(newStateCar: CarManufacturerUiState) {
        _uiStateUpdater.value = newStateCar
    }

    override fun onDestroy() {
        shouldRestoreOdlState = true
    }


}

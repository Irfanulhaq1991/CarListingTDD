package com.irfan.auto1.manufacturers.ui

import androidx.lifecycle.viewModelScope
import com.irfan.auto1.common.BaseViewModel
import com.irfan.auto1.manufacturers.FetchManufacturersUseCase
import com.irfan.auto1.manufacturers.domain.model.CarManufacturer
import kotlinx.coroutines.launch

class CarManufacturersViewModel(private val fetchManufacturersUseCase: FetchManufacturersUseCase) :
    BaseViewModel<CarManufacturer, CarManufacturerUiState, Any>() {
    private var shouldRestoreOdlState = false


    fun fetchManufacturers() {
        if (shouldRestoreOdlState) {
            shouldRestoreOdlState = false
            restore()
        } else
            doFetching()
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
                data = result,
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

    override fun onLoading(stateCar: CarManufacturerUiState?) {
        val newState = (stateCar ?: CarManufacturerUiState()).copy(
            loading = true,
            isError = false
        )
        update(newState)
    }

    override fun onRendered(stateCar: CarManufacturerUiState) {
        val newState = stateCar
            .copy(errorMessage = null, update = false)
        update(newState)
    }

    fun onDestroy(carManufacturers: List<CarManufacturer>) {
        shouldRestoreOdlState = true
        val newState = _uiStateUpdater.value!!.copy(data = carManufacturers)
        update(newState)
    }

    private fun update(newStateCar: CarManufacturerUiState) {
        _uiStateUpdater.value = newStateCar
    }

    private fun restore() {
        shouldRestoreOdlState = false
        val newState = _uiStateUpdater.value!!.copy()
        update(newState)
    }



}

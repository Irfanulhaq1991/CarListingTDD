package com.irfan.auto1.manufacturers.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irfan.auto1.common.BaseViewModel
import com.irfan.auto1.manufacturers.FetchManufacturersUseCase
import com.irfan.auto1.manufacturers.domain.model.Manufacturer
import com.irfan.auto1.year.ui.CarYearsUiState
import kotlinx.coroutines.launch

class ManufacturersViewModel(private val fetchManufacturersUseCase: FetchManufacturersUseCase) :
    BaseViewModel<Manufacturer, ManufacturerUiState, Any>() {
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

    override fun onSuccess(result: List<Manufacturer>, state: ManufacturerUiState?) {
        val newState = (state ?: ManufacturerUiState())
            .copy(
                data = result,
                loading = false,
                isError = false,
            )
        update(newState)
    }

    override fun onError(errorMessage: String, state: ManufacturerUiState?) {
        val newState = (state ?: ManufacturerUiState())
            .copy(
                errorMessage = errorMessage,
                isError = true,
                loading = false
            )
        update(newState)
    }

    override fun onLoading(state: ManufacturerUiState?) {
        val newState = (state ?: ManufacturerUiState()).copy(
            loading = true,
            isError = false
        )
        update(newState)
    }

    override fun onRendered(state: ManufacturerUiState) {
        val newState = state
            .copy(errorMessage = null, update = false)
        update(newState)
    }

    fun onDestroy(manufacturers: List<Manufacturer>) {
        shouldRestoreOdlState = true
        val newState = _uiStateUpdater.value!!.copy(data = manufacturers)
        update(newState)
    }

    private fun update(newState: ManufacturerUiState) {
        _uiStateUpdater.value = newState
    }

    private fun restore() {
        shouldRestoreOdlState = false
        val newState = _uiStateUpdater.value!!.copy()
        update(newState)
    }



}

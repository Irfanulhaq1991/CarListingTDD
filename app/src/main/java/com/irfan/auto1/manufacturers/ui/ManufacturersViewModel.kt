package com.irfan.auto1.manufacturers.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irfan.auto1.manufacturers.FetchManufacturersUseCase
import com.irfan.auto1.manufacturers.domain.model.Manufacturer
import kotlinx.coroutines.launch

class ManufacturersViewModel(private val fetchManufacturersUseCase: FetchManufacturersUseCase) :
    ViewModel() {
    private var shouldRestoreOdlState = false
    private val _uiState = MutableLiveData<ManufacturerUiState>()
    val uiStateUpdater: LiveData<ManufacturerUiState> = _uiState

    fun fetchManufacturers() {
        if (shouldRestoreOdlState) {
            shouldRestoreOdlState = false
            _uiState.value = _uiState.value!!.copy()
        } else
            proceed()

    }

    private fun proceed() {
        viewModelScope.launch {
            _uiState.value = (uiStateUpdater.value ?: ManufacturerUiState()).copy(loading = true, isError = false)
            fetchManufacturersUseCase().run {
                reduceState(this)
            }
        }
    }

    private fun reduceState(result: Result<List<Manufacturer>>) {
        result.fold({
            _uiState.value = uiStateUpdater.value!!
                .copy(
                    manufacturers = it,
                    loading = false,
                    isError =  false
                )

        }, {
            _uiState.value = uiStateUpdater.value!!
                .copy(
                    errorMessage = it.message!!,
                    isError = true,
                    loading = false
                )

        })
    }


    fun stateRendered() {
        _uiState.value = _uiState.value!!.copy(errorMessage = null)
    }

    fun onDestroy(manufacturers: List<Manufacturer>) {
        shouldRestoreOdlState = true
        _uiState.value = _uiState.value!!.copy(manufacturers = manufacturers)
    }

}

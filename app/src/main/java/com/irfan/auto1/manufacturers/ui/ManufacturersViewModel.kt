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
    private val _uiStateUpdater = MutableLiveData<ManufacturerUiState>()
    val uiStateUpdater: LiveData<ManufacturerUiState> = _uiStateUpdater

    fun fetchManufacturers() {
        if (shouldRestoreOdlState) {
            shouldRestoreOdlState = false
            _uiStateUpdater.value = _uiStateUpdater.value!!.copy()
        } else
            proceed()

    }

    private fun proceed() {
        viewModelScope.launch {
            _uiStateUpdater.value = (uiStateUpdater.value ?: ManufacturerUiState()).copy(loading = true, isError = false)
            fetchManufacturersUseCase().run {
                reduceState(this)
            }
        }
    }

    private fun reduceState(result: Result<List<Manufacturer>>) {
        result.fold({
            _uiStateUpdater.value = uiStateUpdater.value!!
                .copy(
                    manufacturers = it,
                    loading = false,
                    isError =  false
                )

        }, {
            _uiStateUpdater.value = uiStateUpdater.value!!
                .copy(
                    errorMessage = it.message!!,
                    isError = true,
                    loading = false
                )

        })
    }


    fun stateRendered() {
        _uiStateUpdater.value = _uiStateUpdater.value!!.copy(errorMessage = null)
    }

    fun onDestroy(manufacturers: List<Manufacturer>) {
        shouldRestoreOdlState = true
        _uiStateUpdater.value = _uiStateUpdater.value!!.copy(manufacturers = manufacturers)
    }

}

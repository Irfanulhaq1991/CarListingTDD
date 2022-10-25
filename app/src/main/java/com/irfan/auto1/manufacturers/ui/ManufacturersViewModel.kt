package com.irfan.auto1.manufacturers.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irfan.auto1.manufacturers.FetchManufacturersUseCase
import com.irfan.auto1.manufacturers.domain.model.Manufacturer
import kotlinx.coroutines.launch

class ManufacturersViewModel(private val fetchManufacturersUseCase: FetchManufacturersUseCase) : ViewModel() {
    private val _uiState = MutableLiveData<ManufacturerUiState>()
    val uiStateUpdater: LiveData<ManufacturerUiState> = _uiState

    fun fetchManufacturers() {
        proceed()

    }

    private fun proceed(){
        viewModelScope.launch {
            _uiState.value = (uiStateUpdater.value?: ManufacturerUiState()).copy(showLoading = true)
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
                    showLoading = false
                )

        }, {
            _uiState.value = uiStateUpdater.value!!
                .copy(
                    errorMessage = it.message!!,
                    showLoading = false
                )

        })
    }


    fun stateRendered() {
        _uiState.value = _uiState.value!!.copy(errorMessage = null)
    }

}

package com.irfan.auto1.manufactureres.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irfan.auto1.manufactureres.FetchManufacturersUseCase
import com.irfan.auto1.manufactureres.domain.model.Manufacturer
import kotlinx.coroutines.launch

class ManufacturersViewModel(private val fetchManufacturersUseCase: FetchManufacturersUseCase) : ViewModel() {
    private val _uiState = MutableLiveData<ManufacturerUiState>()
    val uiState: LiveData<ManufacturerUiState> = _uiState

    fun fetchManufacturers() {
        proceed()

    }

    private fun proceed(){
        viewModelScope.launch {
            _uiState.value = (uiState.value?: ManufacturerUiState()).copy(showLoading = true)
            fetchManufacturersUseCase().run {
                reduceState(this)
            }
        }
    }

    private fun reduceState(result: Result<List<Manufacturer>>) {
        result.fold({
            _uiState.value = uiState.value!!
                .copy(
                    manufacturers = it,
                    showLoading = false
                )

        }, {
            _uiState.value = uiState.value!!
                .copy(
                    errorMessage = it.message!!,
                    showLoading = false
                )

        })
    }

    fun userMessageShown() {
        _uiState.value = uiState.value!!.copy(errorMessage = null)
    }

}

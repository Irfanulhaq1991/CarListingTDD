package com.irfan.auto1.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irfan.auto1.manufacturers.domain.model.Manufacturer
import com.irfan.auto1.manufacturers.ui.ManufacturerUiState
import kotlinx.coroutines.launch

class ModelsViewModel(private val fetchModelsUseCase: FetchModelsUseCase) : ViewModel() {
    private val _uiStateUpdater = MutableLiveData<ModelUiState>()
    val uiStateUpdater: LiveData<ModelUiState> = _uiStateUpdater


    fun fetchModels() {
        proceed()

    }

    private fun proceed() {
        viewModelScope.launch {
            _uiStateUpdater.value = (uiStateUpdater.value ?: ModelUiState()).copy(
                loading = true,
                isError = false
            )
            fetchModelsUseCase().run {
                reduceState(this)
            }
        }
    }

    private fun reduceState(result: Result<List<Model>>) {
        result.fold({
            _uiStateUpdater.value = uiStateUpdater.value!!
                .copy(
                    models = it,
                    loading = false,
                    isError = false
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
}

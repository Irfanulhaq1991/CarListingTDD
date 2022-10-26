package com.irfan.auto1.model.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irfan.auto1.model.domain.usecase.FetchModelsUseCase
import com.irfan.auto1.model.domain.model.Model
import kotlinx.coroutines.launch

class ModelsViewModel(private val fetchModelsUseCase: FetchModelsUseCase) : ViewModel() {
    private val _uiStateUpdater = MutableLiveData<ModelUiState>()
    val uiStateUpdater: LiveData<ModelUiState> = _uiStateUpdater


    fun fetchModels(manufacturerId:Int) {
        proceed(manufacturerId)

    }

    private fun proceed(manufacturerId:Int) {
        viewModelScope.launch {
            _uiStateUpdater.value = (uiStateUpdater.value ?: ModelUiState()).copy(
                loading = true,
                isError = false
            )
            fetchModelsUseCase(manufacturerId).run {
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

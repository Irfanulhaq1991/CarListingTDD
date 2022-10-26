package com.irfan.auto1.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModelsViewModel(useCase: FetchModelsUseCase) : ViewModel() {
    private val _uiStateUpdater = MutableLiveData<ModelUiState>()
    val uiStateUpdater: LiveData<ModelUiState> = _uiStateUpdater

    fun fetchModels() {
        TODO("Not yet implemented")
    }
}

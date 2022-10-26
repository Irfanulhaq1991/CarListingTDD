package com.irfan.auto1.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModelsViewModel(private val useCase: FetchModelsUseCase) : ViewModel() {
    private val _uiStateUpdater = MutableLiveData<ModelUiState>()
    val uiStateUpdater: LiveData<ModelUiState> = _uiStateUpdater

    fun fetchModels() {
        useCase()
    }
}

package com.irfan.auto1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ManufacturersViewModel(private val fetchManufacturersUseCase: FetchManufacturersUseCase) : ViewModel() {
    private val _uiState = MutableLiveData<String>()
    val uiState: LiveData<String> = _uiState

    fun fetchManufacturers() {
        fetchManufacturersUseCase()
    }

}

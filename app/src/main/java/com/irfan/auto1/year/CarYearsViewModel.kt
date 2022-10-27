package com.irfan.auto1.year

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CarYearsViewModel(private val fetchCarYearsUseCase: FetchCarYearsUseCase): ViewModel(){
    private val _uiStateUpdater = MutableLiveData<CarYearsUiState>()
    val uiStateUpdater: LiveData<CarYearsUiState> = _uiStateUpdater

    fun fetchCarYears() {
        fetchCarYearsUseCase()
    }
}

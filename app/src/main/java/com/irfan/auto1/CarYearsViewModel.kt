package com.irfan.auto1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irfan.auto1.model.ui.ModelUiState
import com.irfan.auto1.year.CarYear

class CarYearsViewModel: ViewModel(){
    private val _uiStateUpdater = MutableLiveData<CarYearsUiState>()
    val uiStateUpdater: LiveData<CarYearsUiState> = _uiStateUpdater

    fun fetchCarYears() {
        TODO("Not yet implemented")
    }
}

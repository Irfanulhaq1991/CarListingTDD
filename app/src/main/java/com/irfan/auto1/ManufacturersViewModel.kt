package com.irfan.auto1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ManufacturersViewModel : ViewModel() {
    private val _uiState = MutableLiveData<String>()
    val uiState: LiveData<String> = _uiState

    fun fetchManufacturers() {
        TODO("Not yet implemented")
    }

}

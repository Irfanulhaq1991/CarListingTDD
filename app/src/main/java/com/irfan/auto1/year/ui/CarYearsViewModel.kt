package com.irfan.auto1.year.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irfan.auto1.year.domain.model.CarInfo
import com.irfan.auto1.year.domain.usecase.FetchCarYearsUseCase
import com.irfan.auto1.year.domain.model.CarYear
import kotlinx.coroutines.launch

class CarYearsViewModel(private val fetchCarYearsUseCase: FetchCarYearsUseCase) : ViewModel() {
    private val _uiStateUpdater = MutableLiveData<CarYearsUiState>()
    val uiStateUpdater: LiveData<CarYearsUiState> = _uiStateUpdater

    fun fetchCarYears(carInfo:CarInfo) {
        proceedFetching(carInfo)
    }

    private fun proceedFetching(carInfo:CarInfo) {
        viewModelScope.launch {
            _uiStateUpdater.value = (uiStateUpdater.value ?: CarYearsUiState()).copy(
                loading = true,
                isError = false
            )
            fetchCarYearsUseCase(carInfo).run {
                reduceState(this, uiStateUpdater.value!!)
            }
        }
    }

    private fun reduceState(result: Result<List<CarYear>>, modelUiState: CarYearsUiState) {
        result.fold({
            _uiStateUpdater.value = modelUiState
                .copy(
                    carYears = it,
                    loading = false,
                    isError = false,
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
        _uiStateUpdater.value = _uiStateUpdater.value!!.copy(errorMessage = null, update = false)
    }
}

package com.irfan.auto1.year.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irfan.auto1.common.BaseViewModel
import com.irfan.auto1.common.CarInfo
import com.irfan.auto1.year.domain.usecase.FetchCarYearsUseCase
import com.irfan.auto1.year.domain.model.CarYear
import kotlinx.coroutines.launch

class CarYearsViewModel(private val fetchCarYearsUseCase: FetchCarYearsUseCase) :
    BaseViewModel<CarYear, CarYearsUiState, CarInfo>() {

    fun fetchCarYears(carInfo: CarInfo) {
        doFetching(carInfo)
    }

    override fun onFetch(param: CarInfo?) {
        viewModelScope.launch {
            fetchCarYearsUseCase(param!!).run {
                reduceState(this)
            }
        }
    }

    override fun onSuccess(result: List<CarYear>, state: CarYearsUiState?) {
        val newState = (state ?: CarYearsUiState())
            .copy(
                data = result,
                loading = false,
                isError = false,
            )
        update(newState)
    }

    override fun onError(errorMessage: String, state: CarYearsUiState?) {
        val newState = (state ?: CarYearsUiState())
            .copy(
                errorMessage = errorMessage,
                isError = true,
                loading = false
            )
        update(newState)
    }

    override fun onLoading(state: CarYearsUiState?) {
        val newState = (state ?: CarYearsUiState()).copy(
            loading = true,
            isError = false
        )
        update(newState)
    }

    override fun onRendered(state: CarYearsUiState) {
        val newState = state
            .copy(errorMessage = null, update = false)
        update(newState)
    }


    private fun update(newState: CarYearsUiState) {
        _uiStateUpdater.value = newState
    }
}

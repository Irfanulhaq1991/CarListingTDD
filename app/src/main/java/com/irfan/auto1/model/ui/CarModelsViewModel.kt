package com.irfan.auto1.model.ui

import androidx.lifecycle.viewModelScope
import com.irfan.auto1.common.BaseViewModel
import com.irfan.auto1.common.CarInfo
import com.irfan.auto1.model.domain.usecase.FetchModelsUseCase
import com.irfan.auto1.model.domain.model.CarModel
import com.irfan.auto1.model.domain.usecase.SearchModelsUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CarModelsViewModel(
    private val fetchModelsUseCase: FetchModelsUseCase,
    private val searchModelsUseCase: SearchModelsUseCase

) : BaseViewModel<CarModel, CarModelUiState, CarInfo>() {

    private var isSearch = false
    private var job: Job = Job()



    fun search(query: String) {
        job.cancel()
        proceedSearching(query)
    }

    private fun proceedSearching(query: String) {

        job = viewModelScope.launch {
            searchModelsUseCase(query).run {
                isSearch = true
                reduceState(this)
            }
        }
    }

    override fun onFetch(param: CarInfo?) {
        viewModelScope.launch {
            fetchModelsUseCase(param!!).run {
                reduceState(this)
            }
        }
    }



    override fun onSuccess(result: List<CarModel>, stateCar: CarModelUiState?) {
        val newState = (stateCar ?: CarModelUiState())
            .copy(
                data = result,
                loading = false,
                isError = false,
                update = isSearch
            )
        update(newState)
    }


    override fun onError(errorMessage: String, stateCar: CarModelUiState?) {
        val newState = (stateCar ?: CarModelUiState())
            .copy(
                errorMessage = errorMessage,
                isError = true,
                loading = false
            )
        update(newState)
    }

    override fun onLoading(stateCar: CarModelUiState?) {
        val newState = (stateCar ?: CarModelUiState()).copy(
            loading = true,
            isError = false
        )
        update(newState)
    }

    override fun onRendered(stateCar: CarModelUiState) {
        isSearch = false
        val newState = stateCar
            .copy(errorMessage = null, update = false)
        update(newState)
    }

    private fun update(newStateCar: CarModelUiState) {
        _uiStateUpdater.value = newStateCar
    }
}

package com.irfan.auto1.model.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irfan.auto1.common.BaseViewModel
import com.irfan.auto1.common.CarInfo
import com.irfan.auto1.manufacturers.ui.ManufacturerUiState
import com.irfan.auto1.model.domain.usecase.FetchModelsUseCase
import com.irfan.auto1.model.domain.model.Model
import com.irfan.auto1.model.domain.usecase.SearchModelsUseCase
import com.irfan.auto1.year.ui.CarYearsUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ModelsViewModel(
    private val fetchModelsUseCase: FetchModelsUseCase,
    private val searchModelsUseCase: SearchModelsUseCase

) : BaseViewModel<Model, ModelUiState, CarInfo>() {

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



    override fun onSuccess(result: List<Model>, state: ModelUiState?) {
        val newState = (state ?: ModelUiState())
            .copy(
                data = result,
                loading = false,
                isError = false,
                update = isSearch
            )
        update(newState)
    }


    override fun onError(errorMessage: String, state: ModelUiState?) {
        val newState = (state ?: ModelUiState())
            .copy(
                errorMessage = errorMessage,
                isError = true,
                loading = false
            )
        update(newState)
    }

    override fun onLoading(state: ModelUiState?) {
        val newState = (state ?: ModelUiState()).copy(
            loading = true,
            isError = false
        )
        update(newState)
    }

    override fun onRendered(state: ModelUiState) {
        isSearch = false
        val newState = state
            .copy(errorMessage = null, update = false)
        update(newState)
    }

    private fun update(newState: ModelUiState) {
        _uiStateUpdater.value = newState
    }
}

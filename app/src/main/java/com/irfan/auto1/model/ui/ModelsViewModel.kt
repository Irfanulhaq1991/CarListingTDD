package com.irfan.auto1.model.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irfan.auto1.model.domain.usecase.FetchModelsUseCase
import com.irfan.auto1.model.domain.model.Model
import com.irfan.auto1.model.domain.usecase.SearchModelsUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ModelsViewModel(
    private val fetchModelsUseCase: FetchModelsUseCase,
    private val searchModelsUseCase: SearchModelsUseCase

) : ViewModel() {

    private val _uiStateUpdater = MutableLiveData<ModelUiState>()
    val uiStateUpdater: LiveData<ModelUiState> = _uiStateUpdater
    private var job: Job = Job()

    fun fetchModels(manufacturerId: Int) {
        proceedFetching(manufacturerId)

    }

    fun search(query: String) {
        proceedSearching(query)
    }

    private fun proceedSearching(query: String) {
        job.cancel()
        job = viewModelScope.launch {
            searchModelsUseCase(query).run {
                reduceState(this,(uiStateUpdater.value?:ModelUiState()).copy(update = true))
            }
        }
    }

    private fun proceedFetching(manufacturerId: Int) {
        viewModelScope.launch {
            _uiStateUpdater.value = (uiStateUpdater.value ?: ModelUiState()).copy(
                loading = true,
                isError = false
            )
            fetchModelsUseCase(manufacturerId).run {
                reduceState(this, uiStateUpdater.value!!)
            }
        }
    }

    private fun reduceState(result: Result<List<Model>>, modelUiState: ModelUiState) {
        result.fold({
            _uiStateUpdater.value = modelUiState
                .copy(
                    data = it,
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

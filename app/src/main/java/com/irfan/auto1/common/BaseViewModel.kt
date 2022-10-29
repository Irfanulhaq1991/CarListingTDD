package com.irfan.auto1.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irfan.auto1.year.domain.model.CarYear

abstract class BaseViewModel<T, UI_STATE, PARAM_TYPE> : ViewModel() {
    protected val _uiStateUpdater = MutableLiveData<UI_STATE>()
    val uiStateUpdater: LiveData<UI_STATE> = _uiStateUpdater


    protected abstract fun onSuccess(result: List<T>, state: UI_STATE?)
    protected abstract fun onError(errorMessage: String, state: UI_STATE?)
    protected abstract fun onLoading(state: UI_STATE?)
    protected abstract fun onRendered(state: UI_STATE)
    protected abstract fun onFetch(param: PARAM_TYPE? = null)
    abstract fun onDestroy()

    fun doFetching(param: PARAM_TYPE? = null) {
        onLoading(uiStateUpdater.value)
        onFetch(param)
    }

    protected fun reduceState(result: Result<List<T>>) {
        result.fold({
            onSuccess(it, uiStateUpdater.value)
        }, {
            onError(it.message!!, uiStateUpdater.value)
        })
    }

    fun renderingFinished() {
        onRendered(uiStateUpdater.value!!)
    }

}
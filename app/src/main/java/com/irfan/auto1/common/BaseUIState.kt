package com.irfan.auto1.common

import com.irfan.auto1.model.domain.model.Model

interface BaseUIState<T> {
    val data: List<T>
    val errorMessage: String?
    val isError: Boolean
    val loading: Boolean
    val update: Boolean
}
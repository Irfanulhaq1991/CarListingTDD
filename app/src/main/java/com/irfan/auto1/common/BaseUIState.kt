package com.irfan.auto1.common

interface BaseUIState<T> {
    val data: List<T>
    val errorMessage: String?
    val isError: Boolean
    val loading: Boolean
    val update: Boolean
}
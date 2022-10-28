package com.irfan.auto1.common

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.manufacturers.data.remote.RemoteDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

abstract class RemoteDataSourceContractTests<T> : BaseTest() {

    abstract fun withNoData(): RemoteDataSource<T>
    abstract fun withData(): RemoteDataSource<T>
    abstract fun withException(e: Exception): RemoteDataSource<T>

    @Test
    fun returnErrorOnZero() = runTest {
        val error = "No record found"
        val modelsRemoteDataSource = withNoData()
        val actual = isFailureWithMessage(modelsRemoteDataSource.doFetching(CarInfo()), error)
        assertThat(actual).isTrue()
    }


    @Test
    fun returnMany() = runTest {
        val modelsRemoteDataSource = withData()

        val actual = modelsRemoteDataSource.doFetching(CarInfo()).getOrThrow().size
        assertThat(actual).isGreaterThan(1)
    }

    @Test
    fun returnNoInternetError() = runTest {
        val modelsRemoteDataSource = withException(IOException())
        val error = "Please check your internet connection"
        val actual = isFailureWithMessage(modelsRemoteDataSource.doFetching(CarInfo()), error)
        assertThat(actual).isTrue()
    }



}


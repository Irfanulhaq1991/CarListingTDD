package com.irfan.auto1.model.fetch

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.common.BaseTest

import com.irfan.auto1.common.TestDataProvider
import com.irfan.auto1.model.data.remote.ModelDto
import com.irfan.auto1.model.domain.mapper.ModelsMapper
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ModelMapperShould : BaseTest() {
    private lateinit var modelsMapper: ModelsMapper

    @Before
    override fun setup() {
        super.setup()
        modelsMapper = ModelsMapper()
    }


    @Test
    fun returnNoModels() = runTest {
        val data = emptyList<ModelDto>()
        val actual = modelsMapper.map(data)
        assertThat(actual).isEmpty()
    }

    @Test
    fun returnOneModel() = runTest {
        val data = TestDataProvider.getModelAsDtos()
        val actual = modelsMapper.map(data)
        assertThat(actual).contains(TestDataProvider.getModelAsDomainModels().first())
    }

    @Test
    fun returnManyModel() = runTest {
        val data = TestDataProvider.getModelAsDtos()
        val actual = modelsMapper.map(data)
        assertThat(actual).containsAnyIn(TestDataProvider.getModelAsDomainModels())
    }


}
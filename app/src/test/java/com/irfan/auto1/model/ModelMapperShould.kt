package com.irfan.auto1.model

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.BaseTest

import com.irfan.auto1.TestDataProvider
import org.junit.Before
import org.junit.Test

class ModelMapperShould : BaseTest(){
    private lateinit var modelsMapper: ModelsMapper

    @Before
    override fun setup() {
        super.setup()
        modelsMapper = ModelsMapper()
    }


    @Test
    fun returnNoModels(){
        val data = emptyList<ModelDto>()
        val actual = modelsMapper.map(data)
        assertThat(actual).isEmpty()
    }
    @Test
    fun returnOneModel(){
        val data = TestDataProvider.getModelAsDtos()
        val actual = modelsMapper.map(data)
        assertThat(actual).contains(TestDataProvider.getModelAsDomainModels().first())
    }
    @Test
    fun returnManyModel(){
        val data = TestDataProvider.getModelAsDtos()
        val actual = modelsMapper.map(data)
        assertThat(actual).containsAnyIn(TestDataProvider.getModelAsDomainModels())
    }



}
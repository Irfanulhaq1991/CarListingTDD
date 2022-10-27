package com.irfan.auto1.model.search

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.BaseTest
import com.irfan.auto1.TestDataProvider
import com.irfan.auto1.model.data.ModelFilter
import com.irfan.auto1.model.domain.model.Model
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ModelFilterShould : BaseTest(){
    private lateinit var modelfilter: ModelFilter

    @Before
    override fun setup() {
        super.setup()
        modelfilter = ModelFilter()
    }

    @Test
    fun returnZero(){
        assertThat(modelfilter.filter("")).isEqualTo(emptyList<Model>())
    }

    @Test
    fun returnOne(){
        val data = TestDataProvider.getModelAsDomainModels()
        val query = data.first().name
        modelfilter.setSearchData(data)
        assertThat(modelfilter.filter(query)).hasSize(1)
    }

    @Test
    fun returnMany(){
        val data = TestDataProvider.getModelAsDomainModels()
        val query = "e"
        modelfilter.setSearchData(data)
        assertThat(modelfilter.filter(query)).hasSize(8)
    }

}
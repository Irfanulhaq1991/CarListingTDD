package com.irfan.auto1

import com.google.common.truth.Truth.assertThat
import org.json.JSONObject
import org.junit.Before
import org.junit.Test

class JsonToDomainManufacturersMapperShould : BaseTest(){
    private lateinit var manufacturersMapper: JsonToDomainManufacturersMapper

    @Before
    override fun setup() {
        super.setup()
        manufacturersMapper = JsonToDomainManufacturersMapper()
    }


    @Test
    fun returnNoManufacturers(){
        val data = JSONObject("{}")
        val actual = manufacturersMapper.map(data)
        assertThat(actual).isEmpty()
    }
    @Test
    fun returnOneManufacturer(){
        val data = JSONObject(TestDataProvider.getManufacturersAsJsonStrings())
        val actual = manufacturersMapper.map(data)
        assertThat(actual).contains(TestDataProvider.getManufacturersAsDomainModels().first())
    }
    @Test
    fun returnManyManufacturers(){
        val data = JSONObject(TestDataProvider.getManufacturersAsJsonStrings())
        val actual = manufacturersMapper.map(data)
        assertThat(actual).containsExactlyElementsIn(TestDataProvider.getManufacturersAsDomainModels())
    }



}
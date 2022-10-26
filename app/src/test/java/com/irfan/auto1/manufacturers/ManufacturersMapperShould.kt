package com.irfan.auto1.manufacturers

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.BaseTest
import com.irfan.auto1.manufacturers.domain.mapper.ManufacturersMapper
import com.irfan.auto1.manufacturers.data.remote.model.ManufacturerDto
import com.irfan.auto1.TestDataProvider
import org.junit.Before
import org.junit.Test

class ManufacturersMapperShould : BaseTest(){
    private lateinit var manufacturersMapper: ManufacturersMapper

    @Before
    override fun setup() {
        super.setup()
        manufacturersMapper = ManufacturersMapper()
    }


    @Test
    fun returnNoManufacturers(){
        val data = emptyList<ManufacturerDto>()
        val actual = manufacturersMapper.map(data)
        assertThat(actual).isEmpty()
    }
    @Test
    fun returnOneManufacturer(){
        val data = TestDataProvider.getManufacturersAsDto()
        val actual = manufacturersMapper.map(data)
        assertThat(actual).contains(TestDataProvider.getManufacturersAsDomainModels().first())
    }
    @Test
    fun returnManyManufacturers(){
        val data = TestDataProvider.getManufacturersAsDto()
        val actual = manufacturersMapper.map(data)
        assertThat(actual).containsExactlyElementsIn(TestDataProvider.getManufacturersAsDomainModels())
    }



}
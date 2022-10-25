package com.irfan.auto1.manufacturers

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.BaseTest
import com.irfan.auto1.manufactureres.domain.mapper.DtoToDomainManufacturersMapper
import com.irfan.auto1.manufactureres.data.remote.model.ManufacturerDto
import com.irfan.auto1.TestDataProvider
import org.junit.Before
import org.junit.Test

class DtoToDomainManufacturersMapperShould : BaseTest(){
    private lateinit var manufacturersMapper: DtoToDomainManufacturersMapper

    @Before
    override fun setup() {
        super.setup()
        manufacturersMapper = DtoToDomainManufacturersMapper()
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
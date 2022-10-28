package com.irfan.auto1.manufacturers

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.common.BaseTest
import com.irfan.auto1.manufacturers.domain.mapper.ManufacturersMapper
import com.irfan.auto1.manufacturers.data.remote.ManufacturerDto
import com.irfan.auto1.common.TestDataProvider
import kotlinx.coroutines.test.runTest
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
    fun returnNoManufacturers()= runTest{
        val data = emptyList<ManufacturerDto>()
        val actual = manufacturersMapper.map(data)
        assertThat(actual).isEmpty()
    }
    @Test
    fun returnOneManufacturer()= runTest{
        val data = TestDataProvider.getManufacturersAsDto()
        val actual = manufacturersMapper.map(data)
        assertThat(actual).contains(TestDataProvider.getManufacturersAsDomainModels().first())
    }
    @Test
    fun returnManyManufacturers()= runTest{
        val data = TestDataProvider.getManufacturersAsDto()
        val actual = manufacturersMapper.map(data)
        assertThat(actual).containsExactlyElementsIn(TestDataProvider.getManufacturersAsDomainModels())
    }



}
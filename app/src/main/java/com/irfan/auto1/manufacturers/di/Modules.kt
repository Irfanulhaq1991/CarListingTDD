package com.irfan.auto1.manufacturers.di

import com.irfan.auto1.manufacturers.FetchManufacturersUseCase
import com.irfan.auto1.manufacturers.data.remote.api.PagingManager
import com.irfan.auto1.manufacturers.data.remote.datasource.IManufacturersRemoteDataSource
import com.irfan.auto1.manufacturers.data.remote.datasource.ManufacturersRemoteDataDataSource
import com.irfan.auto1.manufacturers.data.remote.model.ManufacturerDto
import com.irfan.auto1.manufacturers.data.repository.ManufacturersRepo
import com.irfan.auto1.manufacturers.domain.mapper.ManufacturersMapper
import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.manufacturers.domain.model.Manufacturer
import com.irfan.auto1.manufacturers.ui.ManufacturersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val manufacturerModule = module {
    factory { PagingManager(15) }
    factory<IMapper<List<ManufacturerDto>, List<Manufacturer>>> { ManufacturersMapper() }
    factory<IManufacturersRemoteDataSource> { ManufacturersRemoteDataDataSource(get(), get()) }


    factory { ManufacturersRepo(get(), get()) }
    factory { FetchManufacturersUseCase(get()) }
    viewModel { ManufacturersViewModel(get())}
}









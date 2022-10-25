package com.irfan.auto1.manufactureres.di

import com.irfan.auto1.manufactureres.FetchManufacturersUseCase
import com.irfan.auto1.manufactureres.data.remote.api.PagingManager
import com.irfan.auto1.manufactureres.data.remote.datasource.IManufacturersRemoteDataSource
import com.irfan.auto1.manufactureres.data.remote.datasource.ManufacturersRemoteDataDataSource
import com.irfan.auto1.manufactureres.data.remote.model.ManufacturerDto
import com.irfan.auto1.manufactureres.data.repository.ManufacturersRepo
import com.irfan.auto1.manufactureres.domain.mapper.DtoToDomainManufacturersMapper
import com.irfan.auto1.manufactureres.domain.mapper.IMapper
import com.irfan.auto1.manufactureres.domain.model.Manufacturer
import com.irfan.auto1.manufactureres.ui.ManufacturersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val manufacturerModule = module {
    factory { PagingManager() }
    factory<IMapper<List<ManufacturerDto>, List<Manufacturer>>> { DtoToDomainManufacturersMapper() }
    factory<IManufacturersRemoteDataSource> { ManufacturersRemoteDataDataSource(get(), get()) }


    factory { ManufacturersRepo(get(), get()) }
    factory { FetchManufacturersUseCase(get()) }
    viewModel { ManufacturersViewModel(get())}
}









package com.irfan.auto1.manufacturers.di

import com.irfan.auto1.manufacturers.FetchManufacturersUseCase
import com.irfan.auto1.manufacturers.data.remote.ManufacturersRemoteAPI
import com.irfan.auto1.manufacturers.data.remote.PagingManager
import com.irfan.auto1.manufacturers.data.remote.IManufacturersRemoteDataSource
import com.irfan.auto1.manufacturers.data.remote.ManufacturersRemoteDataDataSource
import com.irfan.auto1.manufacturers.data.remote.ManufacturerDto
import com.irfan.auto1.manufacturers.data.ManufacturersRepo
import com.irfan.auto1.manufacturers.domain.mapper.ManufacturersMapper
import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.manufacturers.domain.model.Manufacturer
import com.irfan.auto1.manufacturers.ui.ManufacturersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


val manufacturerModule = module {
    factory { PagingManager(15) }
    factory<IMapper<List<ManufacturerDto>, List<Manufacturer>>>(named("manufacturerMapper"))
    { ManufacturersMapper() }
    factory<IManufacturersRemoteDataSource> { ManufacturersRemoteDataDataSource(get(), get()) }
    factory { provideManufacturersApi(get()) }

    factory { ManufacturersRepo(get(), get(named("manufacturerMapper"))) }
    factory { FetchManufacturersUseCase(get()) }
    viewModel { ManufacturersViewModel(get())}

}



fun provideManufacturersApi(retrofit: Retrofit): ManufacturersRemoteAPI =
    retrofit.create(ManufacturersRemoteAPI::class.java)







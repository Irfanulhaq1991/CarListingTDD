package com.irfan.auto1.manufacturers.di

import com.irfan.auto1.manufacturers.FetchManufacturersUseCase
import com.irfan.auto1.manufacturers.data.ManufacturersRepo
import com.irfan.auto1.manufacturers.data.remote.*
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
    factory<RemoteDataSource<ManufacturerDto>>(named("manufacturerRemoteDataSource"))
    { ManufacturersRemoteDataSource(get(), get()) }
    factory { provideManufacturersApi(get()) }

    factory { ManufacturersRepo(get(named("manufacturerRemoteDataSource")), get(named("manufacturerMapper"))) }
    factory { FetchManufacturersUseCase(get()) }
    viewModel { ManufacturersViewModel(get())}

}



fun provideManufacturersApi(retrofit: Retrofit): ManufacturersRemoteAPI =
    retrofit.create(ManufacturersRemoteAPI::class.java)







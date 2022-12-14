package com.irfan.auto1.model.di

import com.irfan.auto1.manufacturers.data.remote.RemoteDataSource
import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.model.data.*
import com.irfan.auto1.model.data.remote.CarModelDto
import com.irfan.auto1.model.data.remote.ModelRemoteApi
import com.irfan.auto1.model.data.remote.ModelsRemoteDataSource
import com.irfan.auto1.model.domain.usecase.FetchModelsUseCase
import com.irfan.auto1.model.domain.model.CarModel
import com.irfan.auto1.model.domain.mapper.ModelsMapper
import com.irfan.auto1.model.domain.usecase.SearchModelsUseCase
import com.irfan.auto1.model.ui.CarModelsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val modelModule = module {

    factory<IMapper<List<CarModelDto>, List<CarModel>>>(named("modelMapper"))
    { ModelsMapper() }

    factory<RemoteDataSource<CarModelDto>>(named("modelRemoteDataSource"))
    { ModelsRemoteDataSource(get()) }

    factory { provideModelersApi(get()) }

    factory<BaseModelFilter> { ModelFilter() }

    single { ModelsRepository(get(named("modelMapper")), get(named("modelRemoteDataSource")), get()) }

    factory { FetchModelsUseCase(get()) }

    factory { SearchModelsUseCase(get()) }

    viewModel { CarModelsViewModel(get(), get()) }
}


fun provideModelersApi(retrofit: Retrofit): ModelRemoteApi =
    retrofit.create(ModelRemoteApi::class.java)
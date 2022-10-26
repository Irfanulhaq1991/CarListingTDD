package com.irfan.auto1.model.di

import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.model.data.*
import com.irfan.auto1.model.data.remote.IModelsRemoteDataSource
import com.irfan.auto1.model.data.remote.ModelDto
import com.irfan.auto1.model.data.remote.ModelRemoteApi
import com.irfan.auto1.model.data.remote.ModelsRemoteDataSource
import com.irfan.auto1.model.domain.usecase.FetchModelsUseCase
import com.irfan.auto1.model.domain.model.Model
import com.irfan.auto1.model.domain.mapper.ModelsMapper
import com.irfan.auto1.model.ui.ModelsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val  modelModule = module {
    factory<IMapper<List<ModelDto>, List<Model>>>(named("modelMapper"))
    { ModelsMapper() }

    factory<IModelsRemoteDataSource> { ModelsRemoteDataSource(get()) }
    factory { provideModelersApi(get()) }

    factory{ ModelsRepository(get(named("modelMapper")), get()) }
    factory { FetchModelsUseCase(get()) }
    viewModel { ModelsViewModel(get()) }
}
fun provideModelersApi(retrofit: Retrofit): ModelRemoteApi =
    retrofit.create(ModelRemoteApi::class.java)
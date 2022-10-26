package com.irfan.auto1.model.di

import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.model.AppCache
import com.irfan.auto1.model.IAppCache
import com.irfan.auto1.model.data.*
import com.irfan.auto1.model.data.remote.IModelsRemoteDataSource
import com.irfan.auto1.model.data.remote.ModelDto
import com.irfan.auto1.model.data.remote.ModelRemoteApi
import com.irfan.auto1.model.data.remote.ModelsRemoteDataSource
import com.irfan.auto1.model.domain.usecase.FetchModelsUseCase
import com.irfan.auto1.model.domain.model.Model
import com.irfan.auto1.model.domain.mapper.ModelsMapper
import com.irfan.auto1.model.ui.ModelFragment
import com.irfan.auto1.model.ui.ModelsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val modelModule = module {
    factory<IMapper<List<ModelDto>, List<Model>>>(named("modelMapper"))
    { ModelsMapper() }

//    scope<ModelFragment> {
//        scope<IAppCache<String, List<Model>>> { AppCache<String, List<Model>>() }
//    }


    factory<IModelsRemoteDataSource> { ModelsRemoteDataSource(get()) }
    factory { provideModelersApi(get()) }
    factory { ModelsRepository(get(named("modelMapper")), get(), get()) }
    factory { FetchModelsUseCase(get()) }

    viewModel { ModelsViewModel(get()) }
}

val modelScopedModule= module {
    scope<ModelFragment> {
        scoped<IAppCache<String, List<Model>>> { AppCache() }
    }
}

val modelTestModule = module {
    factory<IAppCache<String, List<Model>>> { AppCache() }

}

fun provideModelersApi(retrofit: Retrofit): ModelRemoteApi =
    retrofit.create(ModelRemoteApi::class.java)
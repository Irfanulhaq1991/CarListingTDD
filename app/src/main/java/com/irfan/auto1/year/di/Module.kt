package com.irfan.auto1.year.di

import com.irfan.auto1.manufacturers.data.remote.RemoteDataSource
import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.year.data.CarYearsRepository
import com.irfan.auto1.year.data.remote.CarYearDto
import com.irfan.auto1.year.data.remote.CarYearsRemoteApi
import com.irfan.auto1.year.data.remote.CarYearsRemoteDataSource
import com.irfan.auto1.year.domain.mapper.CarYearsMapper
import com.irfan.auto1.year.domain.model.CarYear
import com.irfan.auto1.year.domain.usecase.FetchCarYearsUseCase
import com.irfan.auto1.year.ui.CarYearsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val carYearModule = module {

    factory<IMapper<List<CarYearDto>, List<CarYear>>>(named("carYearMapper"))
    { CarYearsMapper() }

    factory { provideCarYearsApi(get()) }

    factory<RemoteDataSource<CarYearDto>>(named("carYearRemoteDataSource"))
    { CarYearsRemoteDataSource(get()) }

    factory {CarYearsRepository(get(named("carYearRemoteDataSource")),get(named("carYearMapper"))) }

    factory { FetchCarYearsUseCase(get()) }

    viewModel { CarYearsViewModel(get()) }
}


fun provideCarYearsApi(retrofit: Retrofit): CarYearsRemoteApi =
    retrofit.create(CarYearsRemoteApi::class.java)
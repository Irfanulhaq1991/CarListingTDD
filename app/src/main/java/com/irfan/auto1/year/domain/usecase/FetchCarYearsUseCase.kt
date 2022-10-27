package com.irfan.auto1.year.domain.usecase

import com.irfan.auto1.year.data.CarYearsRepository
import com.irfan.auto1.year.domain.model.CarInfo
import com.irfan.auto1.year.domain.model.CarYear

class FetchCarYearsUseCase(private val repository: CarYearsRepository) {

    suspend operator fun invoke(carInfo: CarInfo):Result<List<CarYear>>{
       return repository.fetchCarYears(carInfo)
    }
}

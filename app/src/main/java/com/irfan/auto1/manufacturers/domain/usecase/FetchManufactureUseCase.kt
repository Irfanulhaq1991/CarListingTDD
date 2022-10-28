package com.irfan.auto1.manufacturers

import com.irfan.auto1.manufacturers.data.ManufacturersRepo
import com.irfan.auto1.manufacturers.domain.model.CarManufacturer

class FetchManufacturersUseCase(private val  manufacturersRepo: ManufacturersRepo) {

    suspend operator fun invoke():Result<List<CarManufacturer>>{
       return manufacturersRepo.fetchManufacturers()
    }

}

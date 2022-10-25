package com.irfan.auto1.manufacturers

import com.irfan.auto1.manufacturers.data.repository.ManufacturersRepo
import com.irfan.auto1.manufacturers.domain.model.Manufacturer

class FetchManufacturersUseCase(private val  manufacturersRepo: ManufacturersRepo) {

    suspend operator fun invoke():Result<List<Manufacturer>>{
       return manufacturersRepo.fetchManufacturers()
    }

}

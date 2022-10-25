package com.irfan.auto1.manufactureres

import com.irfan.auto1.manufactureres.data.repository.ManufacturersRepo
import com.irfan.auto1.manufactureres.domain.model.Manufacturer

class FetchManufacturersUseCase(private val  manufacturersRepo: ManufacturersRepo) {

    suspend operator fun invoke():Result<List<Manufacturer>>{
       return manufacturersRepo.fetchManufacturers()
    }

}

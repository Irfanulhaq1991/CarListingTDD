package com.irfan.auto1

class FetchManufacturersUseCase(private val  manufacturersRepo: ManufacturersRepo) {

    suspend operator fun invoke():Result<List<Manufacturer>>{
       return manufacturersRepo.fetchManufacturers()
    }

}

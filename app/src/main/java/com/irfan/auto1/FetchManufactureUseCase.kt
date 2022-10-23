package com.irfan.auto1

class FetchManufacturersUseCase(private val  manufacturersRepo: ManufacturersRepo) {

    operator fun invoke(){
        manufacturersRepo.fetchManufacturers()
    }

}

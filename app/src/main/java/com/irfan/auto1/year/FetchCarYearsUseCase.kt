package com.irfan.auto1.year

class FetchCarYearsUseCase(private val repository: CarYearsRepository) {

    operator fun invoke(){
        repository.fetchCarYears()
    }
}

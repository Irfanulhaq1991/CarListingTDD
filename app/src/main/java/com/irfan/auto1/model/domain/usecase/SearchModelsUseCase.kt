package com.irfan.auto1.model.domain.usecase

import com.irfan.auto1.model.data.ModelsRepository
import com.irfan.auto1.model.domain.model.CarModel

class SearchModelsUseCase(private val repo: ModelsRepository) {
    suspend operator fun invoke(query: String): Result<List<CarModel>> {
        return repo.search(query)
    }
}

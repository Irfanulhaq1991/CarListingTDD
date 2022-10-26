package com.irfan.auto1.model.domain.usecase

import com.irfan.auto1.model.data.ModelsRepository
import com.irfan.auto1.model.domain.model.Model

class FetchModelsUseCase(private val repo: ModelsRepository) {
  suspend operator fun invoke(manufacturerId:Int): Result<List<Model>> {
     return repo.fetchModels(manufacturerId)
  }
}

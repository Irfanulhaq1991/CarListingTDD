package com.irfan.auto1.model

class FetchModelsUseCase(private val repo: FetchModelsRepository) {
  operator fun invoke(): Result<List<Model>> {
     return repo.fetchModels()
  }
}

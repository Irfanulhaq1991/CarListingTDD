package com.irfan.auto1.model.domain.usecase

import com.irfan.auto1.common.CarInfo
import com.irfan.auto1.model.data.ModelsRepository
import com.irfan.auto1.model.domain.model.CarModel

class FetchModelsUseCase(private val repo: ModelsRepository) {
  suspend operator fun invoke(carInfo: CarInfo): Result<List<CarModel>> {
     return repo.fetchModels(carInfo)
  }
}

package com.irfan.auto1.model.domain.usecase

import android.app.DownloadManager
import android.graphics.ColorSpace
import com.irfan.auto1.model.data.ModelsRepository
import com.irfan.auto1.model.domain.model.Model

class SearchModelsUseCase(private val repo: ModelsRepository) {
    suspend operator fun invoke(query: String): Result<List<Model>> {
        return repo.search(query)
    }
}

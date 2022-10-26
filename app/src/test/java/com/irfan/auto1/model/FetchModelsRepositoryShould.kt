package com.irfan.auto1.model

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.BaseTest
import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class FetchModelsRepositoryShould : BaseTest() {

    @RelaxedMockK
    private lateinit var modelsRemoteDataSource: ModelsRemoteDataSource

    @RelaxedMockK
    private lateinit var mapper: IMapper<List<ModelDto>, List<Model>>
    private lateinit var repo: FetchModelsRepository

    @Before
    override fun setup() {
        super.setup()
        repo = FetchModelsRepository(mapper, modelsRemoteDataSource)
    }

    @Test
    fun callMapper() {
        every { mapper.map(any()) } answers { emptyList()}
        every { modelsRemoteDataSource.fetchModels() } answers { Result.success(emptyList())}
        repo.fetchModels()
        verify { mapper.map(any()) }
    }

    @Test
    fun callRepo() {
        every { mapper.map(any()) } answers { emptyList()}
        every { modelsRemoteDataSource.fetchModels() } answers { Result.success(emptyList())}

        repo.fetchModels()
        verify { modelsRemoteDataSource.fetchModels() }
    }

    @Test
    fun returnDomainModel() {
        every { mapper.map(any()) } answers { emptyList()}
        every { modelsRemoteDataSource.fetchModels() } answers { Result.success(emptyList())}
        val actual = repo.fetchModels()
        assertThat(actual).isEqualTo(Result.success(emptyList<Model>()))
    }

    @Test
    fun notAlterErrorMessage() {
        val errorMessage = "###"
        every { modelsRemoteDataSource.fetchModels() } answers { Result.failure(Throwable(errorMessage)) }
        val actual = isFailureWithMessage(repo.fetchModels(), errorMessage)
        assertThat(actual).isTrue()
    }
}
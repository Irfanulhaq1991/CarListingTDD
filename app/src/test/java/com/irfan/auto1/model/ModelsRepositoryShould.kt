package com.irfan.auto1.model

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.common.BaseTest
import com.irfan.auto1.common.CarInfo
import com.irfan.auto1.manufacturers.data.remote.RemoteDataSource
import com.irfan.auto1.manufacturers.domain.mapper.IMapper
import com.irfan.auto1.model.data.ModelFilter
import com.irfan.auto1.model.data.remote.ModelDto
import com.irfan.auto1.model.data.ModelsRepository
import com.irfan.auto1.model.domain.model.Model
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any

class ModelsRepositoryShould : BaseTest() {

    @RelaxedMockK
    private lateinit var modelFilter: ModelFilter



    @RelaxedMockK
    private lateinit var modelsRemoteDataSource: RemoteDataSource<ModelDto>

    @RelaxedMockK
    private lateinit var mapper: IMapper<List<ModelDto>, List<Model>>
    private lateinit var repo: ModelsRepository

    @Before
    override fun setup() {
        super.setup()
        repo = ModelsRepository(mapper, modelsRemoteDataSource, modelFilter)
    }

    @Test
    fun callMapper() = runTest {
        coEvery { mapper.map(any()) } answers { emptyList() }
        coEvery { modelsRemoteDataSource.doFetching(any()) } answers { Result.success(emptyList()) }
        repo.fetchModels(CarInfo())
        coVerify { mapper.map(any()) }
    }

    @Test
    fun callRepo() = runTest {
        coEvery { mapper.map(any()) } answers { emptyList() }
        coEvery { modelsRemoteDataSource.doFetching(any()) } answers { Result.success(emptyList()) }

        repo.fetchModels(CarInfo())
        coVerify { modelsRemoteDataSource.doFetching(CarInfo()) }
    }

    @Test
    fun returnDomainModel() = runTest {
        coEvery { mapper.map(any()) } answers { emptyList() }
        coEvery { modelsRemoteDataSource.doFetching(any()) } answers { Result.success(emptyList()) }
        val actual = repo.fetchModels(CarInfo())
        assertThat(actual).isEqualTo(Result.success(emptyList<Model>()))
    }

    @Test
    fun notAlterErrorMessage() = runTest {
        val errorMessage = "###"
        coEvery { modelsRemoteDataSource.doFetching(any()) } answers {
            Result.failure(
                Throwable(
                    errorMessage
                )
            )
        }
        val actual = isFailureWithMessage(repo.fetchModels(CarInfo()), errorMessage)
        assertThat(actual).isTrue()
    }

    @Test
    fun cacheData() = runTest {
        coEvery { mapper.map(any()) } answers { emptyList() }
        coEvery { modelsRemoteDataSource.doFetching(any()) } answers { Result.success(emptyList()) }

        repo.fetchModels(any())
        coVerify { modelFilter.setSearchData(any()) }
    }

    @Test
    fun callModelFilter() = runTest {
        coEvery { mapper.map(any()) } answers { emptyList() }
        coEvery { modelsRemoteDataSource.doFetching(any()) } answers { Result.success(emptyList()) }

        repo.search(any())
        coVerify { modelFilter.filter(any()) }
    }
}
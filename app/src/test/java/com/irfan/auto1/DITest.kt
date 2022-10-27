package com.irfan.auto1

import com.irfan.auto1.common.networkModule
import com.irfan.auto1.manufacturers.di.manufacturerModule
import com.irfan.auto1.model.di.modelModule
import com.irfan.auto1.year.di.carYearModule
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito

class DITest : KoinTest {

    // Declare Mock with Mockito
    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    // verify the Koin configuration
    @Test
    fun checkAllModules() = checkModules {
        modules(manufacturerModule,modelModule,carYearModule, networkModule)
    }
}
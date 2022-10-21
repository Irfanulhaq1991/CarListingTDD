package com.irfan.auto1

import com.google.common.truth.Truth.assertThat
import org.junit.Assert
import org.junit.Test


class ExampleTest {

    @Test
    fun isWorking(){
        Assert.assertEquals(4,2+2)
        assertThat("4")
            .isEqualTo("4")
    }
}
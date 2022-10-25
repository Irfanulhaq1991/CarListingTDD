package com.irfan.auto1.manufacturers

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.manufactureres.data.remote.api.PagingManager
import org.junit.Test
import kotlin.test.assertFails

class PagingManagerShould {

    @Test
    fun returnFirstPage() {
        val pageSize = 2
        val pageManager = PagingManager(pageSize)
        assertThat(pageManager.page()).isEqualTo(0)
    }

    @Test
    fun returnSecondPage() {
        val pageSize = 2
        val pageManager = PagingManager(pageSize)
        pageManager.page()
        assertThat(pageManager.page()).isEqualTo(2)
    }

    @Test
    fun returnThirdPage() {
        val pageSize = 2
        val pageManager = PagingManager(pageSize)
        pageManager.page()
        pageManager.page()
        assertThat(pageManager.page()).isEqualTo(4)
    }

    @Test
    fun returnLastPage() {
        val pageCount = 4
        val pageSize = 15
        val lasPage = (pageCount - 1) * pageSize
        val pageManager = PagingManager(pageSize)
        pageManager.setTotalPages(pageCount)
        pageManager.page()
        pageManager.page()
        pageManager.page()

        assertThat(
            pageManager.page()
        ).isEqualTo(lasPage)
    }

    @Test
    fun returnError(){
        val pageCount = 2
        val pageSize = 2
        val pageManager = PagingManager(pageSize)
        pageManager.setTotalPages(pageCount)


        assertFails("No more pages greater than total pages are allowed"){
            pageManager.page()
            pageManager.page()
            pageManager.page()
        }
    }
}
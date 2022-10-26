package com.irfan.auto1.manufacturers

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.manufacturers.data.remote.PagingManager
import org.junit.Test
import kotlin.test.assertFails

class PagingManagerShould {

    @Test
    fun returnFirstPage() {
        val pageSize = 2
        val pageManager = PagingManager(pageSize)
        assertThat(pageManager.nextPage()).isEqualTo(0)
    }

    @Test
    fun returnSecondPage() {
        val pageSize = 2
        val pageManager = PagingManager(pageSize)
        pageManager.updateNextPage()
        assertThat(pageManager.nextPage()).isEqualTo(1)
    }

    @Test
    fun returnThirdPage() {
        val pageSize = 2
        val pageManager = PagingManager(pageSize)
        pageManager.updateNextPage()
        pageManager.updateNextPage()
        assertThat(pageManager.nextPage()).isEqualTo(2)
    }

    @Test
    fun returnLastPage() {
        val pageCount = 4
        val pageSize = 15
        val pageManager = PagingManager(pageSize)
        pageManager.setTotalPages(pageCount)
        pageManager.updateNextPage()
        pageManager.updateNextPage()
        pageManager.updateNextPage()

        assertThat(
            pageManager.nextPage()
        ).isEqualTo(pageCount-1)
    }

    @Test
    fun returnError(){
        val pageCount = 2
        val pageSize = 2
        val pageManager = PagingManager(pageSize)
        pageManager.setTotalPages(pageCount)
        pageManager.updateNextPage()
        pageManager.updateNextPage()
        pageManager.updateNextPage()

        assertFails("No more pages greater than total pages are allowed"){
            pageManager.nextPage()
        }
    }
}
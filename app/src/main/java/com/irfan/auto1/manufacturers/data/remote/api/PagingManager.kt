package com.irfan.auto1.manufacturers.data.remote.api

import java.lang.IllegalStateException

class PagingManager constructor(val pagSize: Int = 10) {
    private var nextPage = 0
    private var totalPages = 100
    fun nextPage(): Int {
        if (nextPage > totalPages-1) throw IllegalStateException("No more pages greater than total pages are allowed")
        return nextPage
    }

    fun setTotalPages(totalPages: Int) {
        this.totalPages = totalPages
    }

    fun updateNextPage(){
        nextPage += 1
    }

}

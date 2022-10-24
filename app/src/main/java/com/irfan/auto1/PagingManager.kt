package com.irfan.auto1

import java.lang.IllegalStateException

class PagingManager constructor(val pagSize: Int = 10) {
    private var nextPage = 0
    private var totalPages = 100
    fun page(): Int {
        if (nextPage > (totalPages - 1) * pagSize) throw IllegalStateException("No more pages greater than total pages are allowed")
        val page = nextPage
        nextPage += pagSize
        return page
    }

    fun setTotalPages(totalPages: Int) {
        this.totalPages = totalPages
    }

}

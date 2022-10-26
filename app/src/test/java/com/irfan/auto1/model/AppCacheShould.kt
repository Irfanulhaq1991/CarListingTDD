package com.irfan.auto1.model

import com.google.common.truth.Truth.assertThat
import com.irfan.auto1.BaseTest
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class AppCacheShould : BaseTest() {
    private lateinit var cache: IAppCache<String, Int>

    @Before
    override fun setup() {
        super.setup()
        cache = AppCache()
    }

    @Test
    fun getZero() {
        assertThat(cache.get("key")).isNull()
    }

    @Test
    fun getOne() {
        val key = "key"
        cache.put(key, 0)
        assertThat(cache.get(key)).isEqualTo(0)
    }

    @Test
    fun getMany() {
        val key = "key"
        val key1 = "key1"
        cache.put(key, 0)
        cache.put(key1, 1)
        assertThat(cache.get(key)).isEqualTo(0)
        assertThat(cache.get(key1)).isEqualTo(1)
    }

    @Test
    fun replace() {
        val key = "key"
        cache.put(key, 0)
        cache.put(key, 1)
        assertThat(cache.get(key)).isNotEqualTo(0)
        assertThat(cache.get(key)).isEqualTo(1)
    }

    @Test
    fun reset() {
        val key = "key"
        cache.put(key, 0)
        cache.clear()
        assertThat(cache.get(key)).isNull()
    }
}
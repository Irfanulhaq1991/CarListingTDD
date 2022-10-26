package com.irfan.auto1.model

interface IAppCache<K, V> {
    val hasMap: HashMap<K, V>
    fun put(key: K, value: V)
    fun get(key: K): V?
    fun clear()
}
package com.irfan.auto1.model

class AppCache<K, V> : IAppCache<K, V> {
    override val hasMap = hashMapOf<K, V>()
    override fun put(key: K, value: V) {
        hasMap[key] = value
    }

    override fun get(key: K): V? {
        return hasMap[key]
    }

    override fun clear() {
        hasMap.clear()
    }

}

package com.pingpongpacket.truckview.models.persistent

import android.support.v4.util.LruCache


class CachingLru private constructor() {
    private var lru: LruCache<String, Any>
    private object Holder { val INSTANCE = CachingLru() }

    companion object {
        val instance: CachingLru by lazy { Holder.INSTANCE }
    }

    init {
        val cacheSize = 1024
        lru = LruCache(cacheSize)
    }

    fun getLru(): LruCache<String, Any> = lru

}
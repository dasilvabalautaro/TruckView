package com.pingpongpacket.truckview.models.network

import com.pingpongpacket.truckview.models.interfaces.IServiceExchangePost

class ServiceRemotePost: BaseServiceRemote() {
    private val TAG = ServiceRemotePost::class.java.name!!
    private val ERROR = TAG + ": IllegalArgument"

    fun getService(): IServiceExchangePost{
        return getClient().
                create(IServiceExchangePost::class.java)?:
                throw IllegalArgumentException(ERROR)
    }
}
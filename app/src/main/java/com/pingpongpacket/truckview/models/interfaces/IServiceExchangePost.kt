package com.pingpongpacket.truckview.models.interfaces

import com.pingpongpacket.truckview.models.network.MessageOfService
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface IServiceExchangePost {
    @Headers("Cache-Control: no-cache")
    @POST
    fun sendPost(@Header("Cookie") cookie: String,
                 @Url url: String, @Body body: RequestBody):
            Observable<MessageOfService>

}
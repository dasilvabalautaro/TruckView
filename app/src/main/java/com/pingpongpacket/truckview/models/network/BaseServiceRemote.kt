package com.pingpongpacket.truckview.models.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by arturosilva on 15/8/17. Name of the package com.pingpongpacket.truckview.models, proyect TruckView in date 15/8/17
 */
abstract class BaseServiceRemote {
    private var retrofit: Retrofit? = null
    private val BASE_URL = "http://host/"


    fun getClient(): Retrofit{
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

        }
        return retrofit ?: throw IllegalArgumentException()
    }
}
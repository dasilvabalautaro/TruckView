package com.pingpongpacket.truckview.dagger

import com.pingpongpacket.truckview.domain.RequestWeighing
import com.pingpongpacket.truckview.models.network.ServiceRemotePost
import com.pingpongpacket.truckview.tools.RxBroadcastReceiver
import dagger.Module
import dagger.Provides

@Module
class WithoutBuilderModule {
    @Provides
    fun provideServiceRemotePost(): ServiceRemotePost {
        return ServiceRemotePost()
    }

    @Provides
    fun provideRequestWeighing(serviceRemotePost:
                               ServiceRemotePost): RequestWeighing {
        return RequestWeighing(serviceRemotePost)
    }

    @Provides
    fun provideBroadcastReceiver(): RxBroadcastReceiver{
        return RxBroadcastReceiver()
    }
}
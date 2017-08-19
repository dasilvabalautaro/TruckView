package com.pingpongpacket.truckview.models

data class AgentCarrier(var name: String = "",
                        var address: String = "",
                        var service: String = "",
                        var license: String = "",
                        var backPack: Map<String, Any>?) {
}
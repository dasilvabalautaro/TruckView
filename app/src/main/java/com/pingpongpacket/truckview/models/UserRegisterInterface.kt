package com.pingpongpacket.truckview.models

/**
 * Created by arturosilva on 9/8/17. Name of the package com.mobile.castruckview.models, proyect CASTruckView in date 9/8/17
 */
interface UserRegisterInterface {
    fun isSignedIn(): Boolean
    fun createAccount(email: String, password: String, name: String): String
    fun signIn(email: String, password: String): String
    fun signOut()
    fun finish()
}
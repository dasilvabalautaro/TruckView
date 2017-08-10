package com.pingpongpacket.truckview.tools

import android.content.Context

class InputCheck(context: Context) {
    fun validateInputLogin(email: String,
                           password: String, name: String): Boolean{
        return !email.isEmpty() && !password.isEmpty() &&
                EmailValidationUtils.isValidEmail(email) && !name.isEmpty()
    }

    fun validateInputLogin(email: String,
                           password: String): Boolean{
        return !email.isEmpty() && !password.isEmpty() &&
                EmailValidationUtils.isValidEmail(email)
    }

}
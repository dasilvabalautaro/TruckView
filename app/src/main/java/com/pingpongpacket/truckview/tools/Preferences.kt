package com.pingpongpacket.truckview.tools

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {
    val PREFERENCES_FILE_NAME = "com.pingpongpacket.truckview.preferences"
    val NAME_USER = "username"
    val preferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

    var username: String
        get() = preferences.getString(NAME_USER, "")
        set(value) = preferences.edit().putString(NAME_USER, value).apply()

}
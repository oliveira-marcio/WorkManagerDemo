package com.marcio.workmanagerdemo

import android.content.SharedPreferences
import android.util.Log

class TokenService(
    private val sharedPreferences: SharedPreferences
) {
    fun refreshToken() {
        sharedPreferences.edit().putString("token", "new_token").commit()
        Log.v("MyDemo", "refreshToken() executed")
    }
}

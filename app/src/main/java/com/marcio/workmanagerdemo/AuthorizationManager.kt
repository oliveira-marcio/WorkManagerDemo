package com.marcio.workmanagerdemo

import android.content.SharedPreferences

class AuthorizationManager(
    private val tokenService: TokenService,
    private val jobScheduler: JobScheduler,
    private val sharedPreferences: SharedPreferences
) {
    fun start() {
        jobScheduler.start()
    }

    fun refreshToken() {
        tokenService.refreshToken()
    }

    fun revokeAuthorization() {
        jobScheduler.stop()
        sharedPreferences.edit().remove("token").commit()
    }
}

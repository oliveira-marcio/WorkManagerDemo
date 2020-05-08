package com.marcio.workmanagerdemo

class AuthorizationManager(
    private val tokenService: TokenService,
    private val jobScheduler: JobScheduler
) {
    fun start() {
        jobScheduler.start()
    }

    fun refreshToken() {
        tokenService.refreshToken()
    }

    fun revokeAuthorization() {
        jobScheduler.stop()
    }
}

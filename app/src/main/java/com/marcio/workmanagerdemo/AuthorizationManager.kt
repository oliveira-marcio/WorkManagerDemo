package com.marcio.workmanagerdemo

class AuthorizationManager(
    private val tokenService: TokenService
) {
    fun refreshToken() {
        tokenService.refreshToken()
    }
}

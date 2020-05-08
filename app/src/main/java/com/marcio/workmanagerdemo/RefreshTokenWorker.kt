package com.marcio.workmanagerdemo

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class RefreshTokenWorker(
    private val context: Context,
    params: WorkerParameters
) : Worker(context, params) {
    override fun doWork(): Result {
        val authorizationManager = (context as DependencyManager).authorizationManager
        authorizationManager.refreshToken()
        return Result.success()
    }
}

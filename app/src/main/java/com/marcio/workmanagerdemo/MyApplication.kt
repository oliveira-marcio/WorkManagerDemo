package com.marcio.workmanagerdemo

import android.app.Application
import android.content.SharedPreferences
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MyApplication : Application(), DependencyManager {
    override val jobScheduler by lazy {
        RefreshTokenJobScheduler(
            WorkManager.getInstance(applicationContext),
            PeriodicWorkRequestBuilder<RefreshTokenWorker>(4, TimeUnit.HOURS)
                .setInitialDelay(4, TimeUnit.HOURS)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()
        )
    }

    override val authorizationManager by lazy {
        AuthorizationManager(TokenService(sharedPreferences), jobScheduler, sharedPreferences)
    }

    val sharedPreferences: SharedPreferences by lazy {
        FakeSharedPreferences()
    }

    override fun onCreate() {
        super.onCreate()
        authorizationManager.start()
    }
}

package com.marcio.workmanagerdemo

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class MyApplication: Application(), DependencyManager {
    override val jobScheduler by lazy {
        RefreshTokenJobScheduler(
            WorkManager.getInstance(applicationContext),
            OneTimeWorkRequestBuilder<RefreshTokenWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()
        )
    }

//    val periodicRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES).build()

    override val authorizationManager by lazy {
        AuthorizationManager(TokenService(),  jobScheduler)
    }

    override fun onCreate() {
        super.onCreate()
        authorizationManager.start()
    }
}

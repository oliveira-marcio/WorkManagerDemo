package com.marcio.workmanagerdemo

import androidx.work.WorkManager
import androidx.work.WorkRequest

class RefreshTokenJobScheduler(
    private val workManager: WorkManager,
    private val workRequest: WorkRequest
) : JobScheduler {
    override fun start() {
        workManager.enqueue(workRequest)
    }

    override fun stop() {
        workManager.cancelWorkById(workRequest.id)
    }
}

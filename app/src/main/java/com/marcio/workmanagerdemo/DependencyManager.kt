package com.marcio.workmanagerdemo

interface DependencyManager {
    val jobScheduler: JobScheduler
    val authorizationManager: AuthorizationManager
}

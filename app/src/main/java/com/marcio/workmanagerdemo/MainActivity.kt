package com.marcio.workmanagerdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.WorkManager

class MainActivity : AppCompatActivity(), MainContainer {

    override val authorizationManager by lazy {
        (applicationContext as DependencyManager).authorizationManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStop() {
        super.onStop()
        authorizationManager.revokeAuthorization()
    }
}

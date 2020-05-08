package com.marcio.workmanagerdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.WorkManager

class MainActivity : AppCompatActivity(), MainContainer {

    override val jobScheduler by lazy {
        (applicationContext as DependencyManager).jobScheduler
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jobScheduler.start()
    }

    override fun onStop() {
        super.onStop()
        jobScheduler.stop()
    }
}

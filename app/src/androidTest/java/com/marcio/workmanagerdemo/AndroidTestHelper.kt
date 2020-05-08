package com.marcio.workmanagerdemo

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import java.util.concurrent.CountDownLatch

class AndroidTestHelper {
    companion object {
        fun <T : Activity> launchActivity(
            rule: AndroidTestHelperRule<T>,
            jobScheduler: JobScheduler,
            sharedPreferences: SharedPreferences = FakeSharedPreferences(),
            tokenService: TokenService = TokenService(sharedPreferences),
            ruleIntent: Intent = Intent(),
            application: MyApplication = ApplicationProvider.getApplicationContext(),
            lock: CountDownLatch = CountDownLatch(1),
            beforeLaunch: (() -> Unit)? = null
        ) {

            val testAuthorizationManager =
                AuthorizationManager(tokenService, jobScheduler, sharedPreferences)

            setLazyDependency(application, "authorizationManager", testAuthorizationManager)
            setLazyDependency(application, "jobScheduler", jobScheduler)

            testAuthorizationManager.start()

            Espresso.onIdle {
                beforeLaunch?.invoke()
                lock.countDown()
            }

            lock.await()

            rule.launchActivity(ruleIntent)
        }

        private fun <T> setLazyDependency(
            application: Application,
            lazyFieldName: String,
            lazyValue: T
        ) {
            val field = application.javaClass.getDeclaredField("$lazyFieldName\$delegate")
            field.isAccessible = true
            field.set(application, object : Lazy<T> {

                override val value: T = lazyValue

                override fun isInitialized() = true
            })
        }
    }
}

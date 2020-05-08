package com.marcio.workmanagerdemo

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.util.Pair
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.rule.IntentsTestRule
import org.hamcrest.Matcher

class AndroidTestHelperRule<T : Activity>(activity: Class<T>) : IntentsTestRule<T>(activity, true, false) {

    private val intentResponsePairs: MutableList<Pair<Matcher<Intent>, Instrumentation.ActivityResult?>> =
        mutableListOf()

    fun registerIntending(intentMatcher: Matcher<Intent>, intentResponse: Instrumentation.ActivityResult) {
        intentResponsePairs.add(Pair(intentMatcher, intentResponse))
    }

    override fun afterActivityLaunched() {
        super.afterActivityLaunched()
        if (intentResponsePairs.isEmpty()) {
            return
        }
        intentResponsePairs.forEach {
            intending(it.first).respondWith(it.second)
        }
    }
}

package com.example.techtrain.railway

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.techtrain.railway.android.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@RunWith(AndroidJUnit4::class)
@Config(shadows = [ShadowLog::class])
class S13 {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test() {
        Intents.init()
        val scenario = activityScenarioRule.scenario
        val logsOnResumed = ShadowLog.getLogsForTag("MainActivity")
        assertEquals(3, logsOnResumed.size)
        assertTrue(logsOnResumed.any { it.msg.contains("onCreate", ignoreCase = true) })
        assertTrue(logsOnResumed.any { it.msg.contains("onStart", ignoreCase = true) })
        assertTrue(logsOnResumed.any { it.msg.contains("onResume", ignoreCase = true) })

        ShadowLog.reset()
        scenario.moveToState(Lifecycle.State.DESTROYED)
        val logsOnDestroyed = ShadowLog.getLogsForTag("MainActivity")
        assertEquals(3, logsOnDestroyed.size)
        assertTrue(logsOnDestroyed.any { it.msg.contains("onPause", ignoreCase = true) })
        assertTrue(logsOnDestroyed.any { it.msg.contains("onStop", ignoreCase = true) })
        assertTrue(logsOnDestroyed.any { it.msg.contains("onDestroy", ignoreCase = true) })
        Intents.release()
    }
}

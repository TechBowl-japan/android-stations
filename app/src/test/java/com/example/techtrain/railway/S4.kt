package com.example.techtrain.railway

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.techtrain.railway.android.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

@RunWith(AndroidJUnit4::class)
class S4 {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test() {
        Intents.init()
        val scenario = activityScenarioRule.scenario
        scenario.onActivity {
            val contentView = it.findViewById<ViewGroup>(android.R.id.content)
            val rootView = contentView.getChildAt(0) as? ViewGroup
            assertNotNull(rootView, "root viewが見つかりません。")
            val textViews = rootView.children.toList().filterIsInstance<TextView>()
            assertEquals(1, textViews.size, "TextViewが存在しないか、複数のTextViewがレイアウト内に存在しています。")
            val actualText = textViews[0].text.toString()
            assertNotEquals("", actualText, "TextViewに文字が設定されていません。")
            assertNotEquals("Hello Railway!", actualText, "TextViewの文字が設定されていません。")
        }
        Intents.release()
    }
}

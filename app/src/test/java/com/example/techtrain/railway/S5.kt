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
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class S5 {

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

            val displayedText = textViews[0].text.toString()
            assertNotEquals("", displayedText, "TextViewにテキストが設定されていません。")
            val activitySourceFile =
                File("src/main/java/com/example/techtrain/railway/android/MainActivity.kt").absoluteFile
            assertTrue(activitySourceFile.exists(), "MainActivity.ktが見つかりません。")
            assertFalse(
                activitySourceFile.containsOnAnyLine("\"$displayedText\""),
                "MainActivityにTextViewで表示されているテキストが定義されています。"
            )
            val activityLayoutFile =
                File("src/main/res/layout/activity_main.xml").absoluteFile
            assertTrue(activityLayoutFile.exists(), "activity_main.xmlが見つかりません。")
            assertFalse(
                activityLayoutFile.containsOnAnyLine("\"$displayedText\""),
                "レイアウトファイルにTextViewで表示されているテキストが定義されています。"
            )
            val stringResourceFile =
                File("src/main/res/values/strings.xml").absoluteFile
            assertTrue(stringResourceFile.exists(), "strings.xmlが見つかりません。")
            assertTrue(
                stringResourceFile.containsOnAnyLine(">$displayedText<"),
                "strings.xmlにTextViewで表示されているテキストが定義されていません。"
            )
        }
        Intents.release()
    }

    private fun File.containsOnAnyLine(text: String): Boolean =
        useLines { it.any { line -> line.contains(text, false) } }
}

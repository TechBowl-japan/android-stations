package com.example.techtrain.railway

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.techtrain.railway.android.MainActivity
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.util.Locale
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class S6 {

    @Test
    fun test() {
        Intents.init()
        val defaultLocaleScenario = ActivityScenario.launch(MainActivity::class.java)
        var defaultLocaleText: String? = null
        defaultLocaleScenario.onActivity {
            val contentView = it.findViewById<ViewGroup>(android.R.id.content)
            val rootView = contentView.getChildAt(0) as? ViewGroup
            assertNotNull(rootView, "root viewが見つかりません。")
            val textViews = rootView.children.toList().filterIsInstance<TextView>()
            assertEquals(1, textViews.size, "TextViewが存在しないか、複数のTextViewがレイアウト内に存在しています。")

            val displayedText = textViews[0].text.toString()
            defaultLocaleText = displayedText
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
        defaultLocaleScenario.close()

        setLocaleToJa()

        val jaLocaleScenario = ActivityScenario.launch(MainActivity::class.java)
        var jaLocaleText: String? = null
        jaLocaleScenario.onActivity {
            val contentView = it.findViewById<ViewGroup>(android.R.id.content)
            val rootView = contentView.getChildAt(0) as? ViewGroup
            assertNotNull(rootView, "root viewが見つかりません。")
            val textViews = rootView.children.toList().filterIsInstance<TextView>()
            assertEquals(1, textViews.size, "TextViewが存在しないか、複数のTextViewがレイアウト内に存在しています。")

            val displayedText = textViews[0].text.toString()
            jaLocaleText = displayedText
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
                File("src/main/res/values-ja/strings.xml").absoluteFile
            assertTrue(stringResourceFile.exists(), "strings.xmlが見つかりません。")
            assertTrue(
                stringResourceFile.containsOnAnyLine(">$displayedText<"),
                "strings.xmlにTextViewで表示されているテキストが定義されていません。"
            )
        }
        jaLocaleScenario.close()

        assertNotEquals(jaLocaleText, defaultLocaleText, "デバイスの言語が日本語とそれ以外の時で同じテキストが表示されています。")
        Intents.release()
    }

    private fun File.containsOnAnyLine(text: String): Boolean =
        useLines { it.any { line -> line.contains(text, false) } }

    private fun setLocaleToJa() {
        val locale = Locale("ja", "jp")
        Locale.setDefault(locale)
        val res = ApplicationProvider.getApplicationContext<Context>().resources
        val config = res.configuration
        config.locale = locale
        res.updateConfiguration(config, res.displayMetrics)
    }
}

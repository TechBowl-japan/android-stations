package com.example.techtrain.railway

import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.children
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.techtrain.railway.android.MainActivity
import com.google.android.material.textview.MaterialTextView
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(AndroidJUnit4::class)
class S10 {

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
            val editTexts = rootView.children.toList().filterIsInstance<EditText>()
            assertEquals(1, editTexts.size, "EditTextが存在しないか、複数のEditTextがレイアウト内に存在しています。")
            val buttons = rootView.children.toList().filterIsInstance<Button>()
            assertEquals(1, buttons.size, "Buttonが存在しないか、複数のButtonがレイアウト内に存在しています。")

            val textView = textViews[0]
            val editText = editTexts[0]
            val button = buttons[0]

            val expectedText = "Modified ${System.currentTimeMillis()}"
            editText.setText(expectedText)
            button.callOnClick()

            assertEquals(expectedText, textView.text.toString())
            try {
                // クラスが存在するかを確認
                Class.forName("com.example.techtrain.railway.android.databinding.ActivityMainBinding")
                assertTrue(true)  // クラスが存在する場合
            } catch (e: ClassNotFoundException) {
                fail("com.example.techtrain.railway.android.databinding.ActivityMainBinding クラスが見つかりません。")
            }
            try {
                // MainActivityのソースコードパス（パスはプロジェクトの構成により異なります）
                val mainActivityPath = "src/main/java/com/example/techtrain/railway/android/MainActivity.kt"
                val path = Paths.get(mainActivityPath)

                // ソースコードを読み込み
                val content = String(Files.readAllBytes(path))

                // findViewByIdがコード内に含まれていないことを確認
                if (content.contains("findViewById")) {
                    fail("MainActivityでfindViewByIdが使用されています。")
                }

            } catch (e: Exception) {
                e.printStackTrace()
                fail("MainActivityのソースコードをチェック中にエラーが発生しました。")
            }
        }
        Intents.release()
    }
}

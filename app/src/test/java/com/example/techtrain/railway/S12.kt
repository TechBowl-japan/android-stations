package com.example.techtrain.railway

import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.view.children
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.techtrain.railway.android.MainActivity
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(AndroidJUnit4::class)
class S12 {

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
            val editTexts = rootView.children.toList().filterIsInstance<EditText>()
            assertEquals(1, editTexts.size, "EditTextが存在しないか、複数のEditTextがレイアウト内に存在しています。")
            val buttons = rootView.children.toList().filterIsInstance<Button>()
            assertEquals(1, buttons.size, "Buttonが存在しないか、複数のButtonがレイアウト内に存在しています。")

            val editText = editTexts[0]
            val button = buttons[0]

            val expectedText = "Modified ${System.currentTimeMillis()}"
            editText.setText(expectedText)
            button.callOnClick()

            intended(
                allOf(
                    hasComponent(hasShortClassName(not(`is`(".MainActivity")))),
                    hasExtra(`is`("KEY_INPUT_TEXT"), `is`(expectedText))
                )
            )
        }
        Intents.release()
    }
}

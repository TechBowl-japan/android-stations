package com.example.techtrain.railway

import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.ImageView
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
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail

@RunWith(AndroidJUnit4::class)
class S7 {

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
            val imageViews = rootView.children.toList().filterIsInstance<ImageView>()
            assertEquals(1, imageViews.size, "ImageViewが存在しないか、複数のImageViewがレイアウト内に存在しています。")

            val imageView = imageViews[0]
            val colorDrawable = imageView.drawable as? ColorDrawable
            assertNotNull(colorDrawable, "ImageViewの色が設定されていません。")
            assertEquals(
                -16777216 /* 0xFF000000 */,
                colorDrawable.color,
                "ImageViewの色が黒(#000000)ではありません。"
            )

            val activityLayoutFile =
                File("src/main/res/layout/activity_main.xml").absoluteFile
            assertTrue(activityLayoutFile.exists(), "activity_main.xmlが見つかりません。")

            val widthMatchParentCount =
                activityLayoutFile.getAppearedCount("android:layout_width=\"match_parent\"")
            if (widthMatchParentCount >= 2) {
                fail(
                    """
                        ConstraintLayoutの中で、layout_width・layout_heightに対して `match_parent` を使用するのは推奨されていません。
                        https://developer.android.com/training/constraint-layout?hl=ja#adjust-the-view-size
                        """.trimIndent()
                )
            }

            assertTrue(
                activityLayoutFile.containsOnAnyLine("android:layout_width=\"0dp\""),
                "ImageViewの横幅が正しく設定されていません。"
            )
            assertTrue(
                activityLayoutFile.containsOnAnyLine("android:layout_height=\"50dp\""),
                "ImageViewの高さが正しく設定されていません。"
            )
            assertTrue(
                activityLayoutFile.containsOnAnyLine("app:layout_constraintEnd_toEndOf=\"parent\""),
                "ImageViewの右方向への制約が正しく設定されていません。"
            )
            assertTrue(
                activityLayoutFile.containsOnAnyLine("app:layout_constraintStart_toStartOf=\"parent\""),
                "ImageViewの左方向への制約が正しく設定されていません。"
            )
            assertTrue(
                activityLayoutFile.containsOnAnyLine("app:layout_constraintTop_toBottomOf=\"@id/text\""),
                "ImageViewの上方向への制約が正しく設定されていません。"
            )
        }
        Intents.release()
    }

    private fun File.containsOnAnyLine(text: String): Boolean =
        useLines { it.any { line -> line.contains(text, false) } }

    private fun File.getAppearedCount(text: String): Int =
        useLines { it.count { line -> line.contains(text, false) } }

}

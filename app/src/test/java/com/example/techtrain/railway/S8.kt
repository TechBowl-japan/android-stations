package com.example.techtrain.railway

import android.graphics.drawable.BitmapDrawable
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

@RunWith(AndroidJUnit4::class)
class S8 {

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
            val bitmapDrawable = imageView.drawable as? BitmapDrawable
            assertNotNull(bitmapDrawable, "画像リソース外のdrawableがImageViewに設定されています。")

            val mdpiDrawableDirectory = File("src/main/res/drawable-mdpi").absoluteFile
            assertTrue(mdpiDrawableDirectory.isDirectory, "drawable-mdpiディレクトリがありません。")
            assertEquals(
                1,
                mdpiDrawableDirectory.listFiles()?.size,
                "画像が存在しないか、2つ以上の画像がdrawable-mdpiフォルダにあります。"
            )
            val drawableFile = checkNotNull(mdpiDrawableDirectory.listFiles()?.first())
            val drawableName = drawableFile.name

            val hdpiDrawableDirectory = File("src/main/res/drawable-hdpi").absoluteFile
            assertTrue(hdpiDrawableDirectory.isDirectory, "drawable-hdpiディレクトリがありません。")
            val hdpiDrawableFile = hdpiDrawableDirectory.resolve(drawableName)
            assertTrue(hdpiDrawableFile.exists(), "drawable-hdpi内にmdpiと同じ名前の画像がありません。")

            val xhdpiDrawableDirectory = File("src/main/res/drawable-xhdpi").absoluteFile
            assertTrue(xhdpiDrawableDirectory.isDirectory, "drawable-xhdpiディレクトリがありません。")
            val xhdpiDrawableFile = xhdpiDrawableDirectory.resolve(drawableName)
            assertTrue(xhdpiDrawableFile.exists(), "drawable-xhdpi内にmdpiと同じ名前の画像がありません。")

            val activityLayoutFile =
                File("src/main/res/layout/activity_main.xml").absoluteFile
            assertTrue(activityLayoutFile.exists(), "activity_main.xmlが見つかりません。")
            assertTrue(
                activityLayoutFile.containsOnAnyLine("android:src=\"@drawable/${drawableFile.nameWithoutExtension}\""),
                "ImageViewに画像が設定されていません。"
            )
        }
        Intents.release()
    }

    private fun File.containsOnAnyLine(text: String): Boolean =
        useLines { it.any { line -> line.contains(text, false) } }
}

package com.example.techtrain.railway

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.techtrain.railway.android.Book
import com.example.techtrain.railway.android.MainActivity
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.reflect.Method
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


@RunWith(AndroidJUnit4::class)
class S15 {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test() {
        val context = ApplicationProvider.getApplicationContext<Application>()

        val info = context.packageManager.getPackageInfo(
            context.packageName,
            PackageManager.GET_PERMISSIONS
        )
        assertTrue(info.requestedPermissions.toSet().containsAll(setOf(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE)), "AndroidManifestに必要なパーミッションが書かれていない")

        val retrofitClass = try {
            Class.forName("retrofit2.Retrofit")
        } catch (_: ClassNotFoundException) {
            null
        }
        assertNotNull(retrofitClass, "Retrofitが依存関係に含まれていません。")
        val moshiConverterFactoryClass = try {
            Class.forName("retrofit2.converter.moshi.MoshiConverterFactory")
        } catch (_: ClassNotFoundException) {
            null
        }
        assertNotNull(moshiConverterFactoryClass, "Retrofit converter-moshiが依存関係に含まれていません。")

        try {
            // BooksService クラスを取得
            val serviceClass = Class.forName("com.example.techtrain.railway.android.BooksService")
            assertNotNull(serviceClass, "BooksService クラスが存在しません。")

            // publicBooks メソッドが存在するかを確認
            val method: Method? = serviceClass.methods.firstOrNull {
                it.name == "publicBooks" && it.parameterTypes.size == 1 && it.parameterTypes[0] == String::class.java
            }
            assertNotNull(method,"BooksService に publicBooks メソッドが存在しません。")
        } catch (e: ClassNotFoundException) {
            fail("com.example.techtrain.railway.android.BooksService クラスが見つかりません。")
        }

        runBlocking {
            val scenario = activityScenarioRule.scenario

            scenario.onActivity {
                val contentView = it.findViewById<ViewGroup>(android.R.id.content)
                val rootView = contentView.getChildAt(0) as? ViewGroup
                assertNotNull(rootView, "root viewが見つかりません。")
                val buttons = rootView.children.toList().filterIsInstance<Button>()
                assertEquals(1, buttons.size, "Buttonが存在しないか、複数のButtonがレイアウト内に存在しています。")
                // メインスレッドでの処理時間を測定
                val timeTaken = measureTimeMillis {
                    // ボタンのクリックをシミュレート
                    buttons[0].performClick()
                }

                // メインスレッドがブロックされた場合、一定時間以上かかる
                if (timeTaken > 100) { // ここでは100msを基準にしています
                    fail("ボタンのクリックイベントがメインスレッドをブロックしています。")
                }
                buttons[0].callOnClick()
            }

            delay(10000L)

            scenario.onActivity {
                val contentView = it.findViewById<ViewGroup>(android.R.id.content)
                val rootView = contentView.getChildAt(0) as? ViewGroup
                assertNotNull(rootView, "root viewが見つかりません。")
                val textViews = rootView.children.toList().filterIsInstance<MaterialTextView>()
                assertEquals(
                    1,
                    textViews.size,
                    "TextViewが存在しないか、複数のTextViewがレイアウト内に存在しています。"
                )
                // 正規表現を使用して result を Book リストにパースする（簡易的な方法）
                val bookList = Regex("""Book\(id=([^,]+), title=([^,]+), url=([^,]+), detail=([^,]+), review=([^,]+), reviewer=([^\)]+)\)""")
                    .findAll(textViews[0].text)
                    .map { match ->
                        Book(
                            id = match.groupValues[1],
                            title = match.groupValues[2],
                            url = match.groupValues[3],
                            detail = match.groupValues[4],
                            review = match.groupValues[5],
                            reviewer = match.groupValues[6]
                        )
                    }.toList()
                // テキストビューの内容を確認
                assertTrue(bookList.isNotEmpty(), "結果が正しくTextViewに表示されていないかoffsetを0から取得していないか")
            }
        }
    }
}

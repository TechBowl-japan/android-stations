package com.example.techtrain.railway

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.children
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.techtrain.railway.android.MainActivity
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
                val textViews = rootView.children.toList().filterIsInstance<TextView>()
                assertEquals(
                    1,
                    textViews.size,
                    "TextViewが存在しないか、複数のTextViewがレイアウト内に存在しています。"
                )
                val result = "[Book(id=b011e918-63a9-4683-ad5d-1bdf87d065e6, title=taikun, url=https://www.yahoo.co.jp/, detail=test, review=tai, reviewer=tai), Book(id=7d8ad2d6-7349-48fe-addb-920fd6917609, title=React, url=https://www.yahoo.co.jp/, detail=ken, review=ken, reviewer=kens), Book(id=2811e448-6491-4324-9c01-1b131c4cca45, title=React, url=https://www.yahoo.co.jp/, detail=test, review=test, reviewer=kens), Book(id=a745e46e-fbd6-4a65-9926-56d33f1ee53a, title=React, url=https://www.yahoo.co.jp/, detail=test, review=test, reviewer=kens), Book(id=1e02b253-dd0a-4378-a4e1-d16417e1d5de, title=Git, url=https://www.yahoo.co.jp/, detail=test, review=test, reviewer=kens), Book(id=c169a916-fa90-4b90-841f-3e2d73302a1e, title=てすと, url=https://www.google.com/?hl=ja, detail=ぐーぐる, review=ぐーぐる, reviewer=na), Book(id=3c714672-c3be-4192-87c9-b09c2ef56d5c, title=ぐーぐる, url=https://www.google.com/?hl=ja, detail=ぐーぐる, review=ぐーぐる, reviewer=na), Book(id=5a29f82b-0de6-42bb-8c6e-1244b28757b2, title=てすと, url=https://www.google.com/?hl=ja, detail=テスト, review=テスト, reviewer=na), Book(id=58b7b376-e4fe-4650-abd7-ccc14da09a5b, title=Getting to Yes, url=https://www.amazon.co.jp/-/en/Roger-Fisher/dp/0143118757, detail=\"Getting to Yes has helped millions of people learn a better way to negotiate. One of the primary business texts of the modern era, it is based on the work of the Harvard Negotiation Project, a group that deals with all levels of negotiation and conflict resolution.\n\nGetting to Yes offers a proven, step-by-step strategy for coming to mutually acceptable agreements in every sort of conflict. Thoroughly updated and revised, it offers readers a straight- forward, universally applicable method for negotiating personal and professional disputes without getting angry-or getting taken.\" -from Amazon.com, review=I have not read this book yet I want to complete in somedays, reviewer=Sugar), Book(id=c39146e8-536a-441f-ad04-47095b1c2696, title=thridweb, url=https://thirdweb.com/, detail=API for web3, review=lets's create somethings new in web3!, reviewer=Sugar)]"
                // テキストビューの内容を確認
                assertEquals(textViews[0].text, result, "結果が正しくTextViewに表示されていないかoffsetを0から取得していないか")
            }
        }
    }
}

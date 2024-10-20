package com.example.techtrain.railway

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.techtrain.railway.android.MainActivity
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class S17 {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test() {
        val retrofitClass = try {
            Class.forName("androidx.recyclerview.widget.RecyclerView")
        } catch (_: ClassNotFoundException) {
            null
        }
        assertNotNull(retrofitClass, "RecyclerViewが依存関係に含まれていません。")
        val scenario = activityScenarioRule.scenario
        runBlocking {
            delay(10000L)
            scenario.onActivity {
                val contentView = it.findViewById<ViewGroup>(android.R.id.content)
                val rootView = contentView.getChildAt(0) as? ViewGroup
                assertNotNull(rootView,"root viewが見つかりません。")

                // RecyclerViewのテスト
                val recyclerViews = rootView.children.toList().filterIsInstance<RecyclerView>()
                assertEquals(1, recyclerViews.size, "RecyclerViewが存在しないか、複数のRecyclerViewがレイアウト内に存在しています。")
                val recyclerView = recyclerViews[0]
                assertNotNull(recyclerView,"RecyclerViewが見つかりません。")

                // RecyclerViewのアダプターを使用してアイテム数を確認
                val adapter = recyclerView.adapter
                assertNotNull(adapter,"RecyclerViewのアダプターが設定されていません。")

                // アダプターのアイテム数を確認
                val itemCount = adapter.itemCount
                assertEquals( 10, itemCount, "RecyclerViewのアイテム数が正しくありません。")
                // RecyclerViewの各アイテムがBookItemViewであることを確認
                val titleResults = listOf("taikun", "React", "React", "React", "Git", "てすと", "ぐーぐる", "てすと", "Getting to Yes", "thridweb")

                val detailResults = listOf(
                    "test",
                    "ken",
                    "test",
                    "test",
                    "test",
                    "ぐーぐる",
                    "ぐーぐる",
                    "テスト",
                    "Getting to Yes has helped millions of people learn a better way to negotiate. One of the primary business texts of the modern era, it is based on the work of the Harvard Negotiation Project, a group that deals with all levels of negotiation and conflict resolution. Getting to Yes offers a proven, step-by-step strategy for coming to mutually acceptable agreements in every sort of conflict. Thoroughly updated and revised, it offers readers a straightforward, universally applicable method for negotiating personal and professional disputes without getting angry-or getting taken.",
                    "API for web3"
                )

                for (i in 0 until itemCount) {
                    val viewHolder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i))
                    adapter.bindViewHolder(viewHolder, i)

                    // ViewHolderがBookItemViewを含んでいるか確認
                    val itemView = viewHolder.itemView
                    assertNotNull(itemView,"BookItemViewが見つかりません。")
                    assertEquals("BookItemView", itemView::class.simpleName, "BookItemViewのタイプが一致しません。")

                    val textViews = (itemView as ViewGroup).children.toList().filterIsInstance<MaterialTextView>()

                    assertTrue(2 < recyclerViews.size, "TextViewは少なくとも、titleとdetail２個が必要")
                    assertTrue(textViews.map { it.text }.toSet().containsAll(setOf(titleResults[i], detailResults[i])), "${i}番めのtitleとdetailがデータと一致しない")
                }
            }
        }
    }
}

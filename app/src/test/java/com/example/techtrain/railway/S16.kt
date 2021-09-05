package com.example.techtrain.railway

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertNotNull

@RunWith(AndroidJUnit4::class)
class S16 {

    @Test
    fun test() {
        val retrofitClass = try {
            Class.forName("androidx.recyclerview.widget.RecyclerView")
        } catch (_: ClassNotFoundException) {
            null
        }
        assertNotNull(retrofitClass, "RecyclerViewが依存関係に含まれていません。")
    }
}

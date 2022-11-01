package com.example.qiraatalkarim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.qiraatalkarim.data.MyPlayerService
import com.example.qiraatalkarim.data.Quran
import com.example.qiraatalkarim.data.QuranDatabase
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import snow.player.PlayerClient

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val database = QuranDatabase.getInstance(this)
        val search: TextInputEditText = findViewById(R.id.search_bar)

        search.setOnEditorActionListener { textView, id, keyEvent ->
            if(id == EditorInfo.IME_ACTION_DONE){
                val query = "%${textView.text}%"
                lifecycleScope.launch {
                    val quranSearchList = database.quranDao().searchAyah(query)
                    setQuranAdapter(quranSearchList)
                }
                true
            }
            false
        }
    }
    fun setQuranAdapter(quranList: List<Quran>){
        val recyclerView = findViewById<RecyclerView>(R.id.rv_search)

        val adapter = SearchAdapter(quranList)
        recyclerView.adapter = adapter
    }
}
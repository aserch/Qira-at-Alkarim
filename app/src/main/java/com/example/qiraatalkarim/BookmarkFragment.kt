package com.example.qiraatalkarim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.qiraatalkarim.data.QuranDatabase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BookmarkFragment : Fragment(R.layout.bookmark_fragment) {

    lateinit var recyclerView:RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_bookmark)
    }

    override fun onStart() {
        super.onStart()
        val database = QuranDatabase.getInstance(requireContext())
        lifecycleScope.launch {
            val bookmark = database.quranDao().getBookmark()
            bookmark.collect{
                val adapter = BookmarkAdapter(it)
                recyclerView.adapter = adapter
                adapter.itemClickListener ={
                    val intent =  Intent(this@BookmarkFragment.context, QuranActivity::class.java)
                    intent.putExtra(QuranActivity.AYAH_NUMBER, it.ayahNumber)
                    intent.putExtra(QuranActivity.SURAH_NUMBER, it.SurahNumber)
                    startActivity(intent)
                }

                adapter.deleteClickListener = {
                    lifecycleScope.launch {
                        database.quranDao().deleteBookmark(it)
                    }

                }
            }
        }
    }
}
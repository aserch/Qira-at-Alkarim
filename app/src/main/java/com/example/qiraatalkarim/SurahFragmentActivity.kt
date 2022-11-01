package com.example.qiraatalkarim

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qiraatalkarim.data.QuranDatabase
import com.example.qiraatalkarim.data.Surah
import com.l4digital.fastscroll.FastScrollRecyclerView
import kotlinx.coroutines.launch

class SurahFragmentActivity: Fragment(R.layout.layout_fragment_surah) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<FastScrollRecyclerView>(R.id.recyclerview_surah)
        val dataBase = QuranDatabase.getInstance(requireContext())

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter
        }

        lifecycleScope.launch{
            val surahList = dataBase.quranDao().getSurahList()
            val adapter = SurahAdapter(surahList)
            recyclerView.adapter = adapter


            adapter.itemClickListener = {
                val intent = Intent(requireContext(), QuranActivity::class.java)
                intent.putExtra(QuranActivity.SURAH_NUMBER, it.surahNumber)
                intent.putExtra("total", it.totalSurah)
                startActivity(intent)
            }
        }

    }

}
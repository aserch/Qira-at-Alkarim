package com.example.qiraatalkarim

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.qiraatalkarim.data.Quran
import com.example.qiraatalkarim.data.QuranDatabase
import kotlinx.coroutines.launch

class PageFragmentActivity: Fragment(R.layout.layout_fragment_page) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_page)
        val database = QuranDatabase.getInstance(requireContext())

        lifecycleScope.launch{
            val pageList = database.quranDao().getPageList()
            val adapter = PageAdapter(pageList)
            recyclerView.adapter = adapter

            adapter.itemClickListener = {
                val intent = Intent(requireContext(),QuranActivity::class.java)
                intent.putExtra(QuranActivity.PAGE_NUMBER, it.page)
                intent.putExtra("tab_index", 2)
                startActivity(intent)
            }
        }
    }
}
package com.example.qiraatalkarim

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.qiraatalkarim.data.QuranDatabase
import kotlinx.coroutines.launch

class JuzFragmentActivity: Fragment(R.layout.layout_fragment_juz) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_juz)
        val database = QuranDatabase.getInstance(requireContext())

        lifecycleScope.launch{
            val juzList = database.quranDao().getJuzList()
            val adapter = JuzAdapter(juzList)
            recyclerView.adapter = adapter

            adapter.itemClickListener = {
                val intent = Intent(requireContext(),QuranActivity::class.java)
                intent.putExtra(QuranActivity.JUZ_NUMBER, it.juzNumber)
                intent.putExtra("tab_index", 1)
                startActivity(intent)
            }
        }
    }

}
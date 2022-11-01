package com.example.qiraatalkarim

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qiraatalkarim.data.Surah
import com.l4digital.fastscroll.FastScroller

class SurahAdapter(val surahList: List<Surah>): RecyclerView.Adapter<SurahAdapter.surahViewHolder>(), FastScroller.SectionIndexer {

    override fun getSectionText(position: Int): CharSequence {
        return "Surah ${surahList[position].surahNameEnglish}"
    }


    var itemClickListener:((Surah)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): surahViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_surat, parent, false)
        return surahViewHolder(view)
    }

    override fun onBindViewHolder(holder: surahViewHolder, position: Int) {
        val surah: Surah = surahList[position]
        holder.bindView(surah)
        holder.itemView.setOnClickListener{
            itemClickListener?.invoke(surah)
        }
    }

    override fun getItemCount(): Int {
        return surahList.size
    }

    class surahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textSurahName = itemView.findViewById<TextView>(R.id.text_surah_name)
        val textSurahNumber = itemView.findViewById<TextView>(R.id.text_surah_number)
        val textTotalAyah = itemView.findViewById<TextView>(R.id.text_total_ayah)
        val textArab:TextView = itemView.findViewById(R.id.text_arab_surat)
        fun bindView(surah: Surah){
            textArab.text = surah.surahNameArabic
            textSurahName.text = surah.surahNameEnglish
            textSurahNumber.text = surah.surahNumber.toString()
            textTotalAyah.text = "${surah.totalSurah} ayah"
        }
    }



}
package com.example.qiraatalkarim

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qiraatalkarim.data.Juzz

class JuzAdapter(val juzList: List<Juzz>): RecyclerView.Adapter<JuzAdapter.juzViewHolder>() {

    var itemClickListener: ((Juzz)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): juzViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_juz, parent, false)
        return juzViewHolder(view)
    }

    override fun onBindViewHolder(holder: juzViewHolder, position: Int) {
        val juz = juzList[position]
        holder.bindView(juz)
        holder.itemView.setOnClickListener{
            itemClickListener?.invoke(juz)
        }
    }

    override fun getItemCount(): Int {
        return juzList.size
    }

    class juzViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textJuzName = itemView.findViewById<TextView>(R.id.text_juz_name)
        val textJuzStart = itemView.findViewById<TextView>(R.id.text_juz_start)
        val textJuzNumber = itemView.findViewById<TextView>(R.id.text_juz_number)
        fun bindView(juz: Juzz){
            textJuzName.text = "Juz ${juz.juzNumber}"
            textJuzStart.text = "${juz.surahName}: ${juz.ayahNumber}"
            textJuzNumber.text = juz.juzNumber.toString()
        }
    }
}
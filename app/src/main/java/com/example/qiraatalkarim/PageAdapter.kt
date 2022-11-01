package com.example.qiraatalkarim

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qiraatalkarim.data.Pages
import com.example.qiraatalkarim.data.Surah

class PageAdapter(val pageList: List<Pages>): RecyclerView.Adapter<PageAdapter.pageViewHolder>() {

    var itemClickListener:((Pages)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): pageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false)
        return pageViewHolder(view)
    }

    override fun onBindViewHolder(holder: pageViewHolder, position: Int) {
        val page: Pages = pageList[position]
        holder.bindView(page)
        holder.itemView.setOnClickListener{
            itemClickListener?.invoke(page)
        }
    }

    override fun getItemCount(): Int {
        return pageList.size
    }

    class pageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textPageNumber = itemView.findViewById<TextView>(R.id.text_page_number)
        val textPageName = itemView.findViewById<TextView>(R.id.text_page_name)
        val textPageStart = itemView.findViewById<TextView>(R.id.text_page_start)
        fun bindView(page: Pages){
            textPageNumber.text = page.page.toString()
            textPageName.text = page.surahNameEnglish
            textPageStart.text = "${page.surahNameEnglish}: ${page.ayahNumber}"
        }
    }
}
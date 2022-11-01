package com.example.qiraatalkarim

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qiraatalkarim.data.Bookmark

class BookmarkAdapter(val bookmarkList: List<Bookmark>): RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    var itemClickListener:((Bookmark) -> Unit)? = null
    var deleteClickListener:((Bookmark) -> Unit)? = null

    class BookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textSurahNameEn:TextView = itemView.findViewById(R.id.text_surah_name)
        val textAyahNumber:TextView = itemView.findViewById(R.id.text_ayah)
        val sampah:ImageView = itemView.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bookmark, parent, false)
        return BookmarkViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val bookmark: Bookmark = bookmarkList[position]
        holder.textSurahNameEn.text = bookmark.surahNameEn
        holder.sampah.setOnClickListener{
            deleteClickListener?.invoke(bookmark)
        }
        holder.textAyahNumber.text = bookmark.ayahNumber.toString()
        holder.itemView.setOnClickListener{
            itemClickListener?.invoke(bookmark)
        }
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }
}
package com.example.qiraatalkarim

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.qiraatalkarim.data.Quran

class SearchAdapter(val quranList: List<Quran>): RecyclerView.Adapter<SearchAdapter.SearchViewholder>() {

    var itemClickListener: ((Quran) -> Unit)? = null
    var itemClickListener2: ((Quran) -> Unit)? = null
    var shareClickListener: ((Quran) -> Unit)? = null
    var playerClickListener: ((Quran) -> Unit)? = null
    var playAllClickListener: ((List<Quran>) -> Unit)? = null
    var footNotesClickListener: ((Quran) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tampilan_surah, parent, false)
        return SearchViewholder(view)
    }

    override fun onBindViewHolder(holder: SearchViewholder, position: Int) {
        val quran: Quran = quranList[position]
        holder.bindView(quran)
        holder.surahExplanation.isVisible = quran.ayahNumber == 1
        holder.ayahNumber.isVisible = false
        holder.buttonCopy.setOnClickListener{
            itemClickListener2?.invoke(quran)
        }
        holder.itemView.setOnClickListener{
            itemClickListener?.invoke(quran)
        }
        holder.buttonShare.setOnClickListener {
            shareClickListener?.invoke(quran)
        }
        holder.btnSoundPlayer.setOnClickListener {
            playerClickListener?.invoke(quran)
        }
        holder.btnPlayAll.setOnClickListener {
            playAllClickListener?.invoke(quranList)
        }
        holder.textTranslation.setOnClickListener{
            footNotesClickListener?.invoke(quran)
        }

    }

    override fun getItemCount(): Int {
        return quranList.size
    }

    class SearchViewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val surahExplanation = itemView.findViewById<CardView>(R.id.surah_explanation)
        val textSurahName = itemView.findViewById<TextView>(R.id.text_surah_name)
        val textAyah = itemView.findViewById<TextView>(R.id.text_ayah_quran)
        val textTranslation = itemView.findViewById<TextView>(R.id.text_ayah_translation)
        val buttonCopy = itemView.findViewById<ImageButton>(R.id.button_copy)
        val buttonShare: ImageButton = itemView.findViewById(R.id.button_share)
        val ayahNumber: TextView = itemView.findViewById(R.id.number_ayah)
        val pembuka: TextView = itemView.findViewById(R.id.awal)
        val btnSoundPlayer: ImageButton = itemView.findViewById(R.id.button_play)
        val btnPlayAll: Button = itemView.findViewById(R.id.btn_play_all)
        val jumlahAyat: TextView = itemView.findViewById(R.id.text_total_ayah)
        val surahEx: TextView = itemView.findViewById(R.id.explanation)

        fun bindView(quran: Quran){
            textSurahName.text = "${quran.surahNameEnglish} (${quran.surahNameArabic})"
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.S){
                textAyah.text = quran.ayahText
            }else{
                textAyah.text = reverseAyahNumber(quran.ayahText.toString())
            }

            textTranslation.text = "${quran.ayahNumber}. ${quran.translation}"
            ayahNumber.text = quran.ayahNumber.toString()

            if(quran.surahNumber == 1 && quran.ayahNumber == 1){
                pembuka.setText("أَعُوذُ بِاللَّهِ مِنَ الشَّيْطَانِ الرَّجِيمِ")
            }else{
                pembuka.setText("بِسۡمِ ٱللَّهِ ٱلرَّحۡمَٰنِ ٱلرَّحِيم")
            }

            surahEx.text = "${quran.surahNameEnglish}: ${quran.ayahNumber}"
        }

        fun reverseAyahNumber(ayahText: String): String{
            val digits = mutableListOf<Char>()
            ayahText.forEach { char ->
                if(char.isDigit()){
                    digits.add(char)
                }
            }
            val reversDigits = digits.reversed()
            return ayahText.replace(digits.joinToString(""), reversDigits.joinToString(""))
        }
    }
}
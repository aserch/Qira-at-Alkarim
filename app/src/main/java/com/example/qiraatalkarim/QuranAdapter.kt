package com.example.qiraatalkarim

import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.SuperscriptSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.qiraatalkarim.data.Bookmark
import com.example.qiraatalkarim.data.Quran
import com.example.qiraatalkarim.utils.QuranArabicUtils
import com.l4digital.fastscroll.FastScrollRecyclerView
import com.l4digital.fastscroll.FastScroller
import java.lang.Exception
import java.util.regex.Matcher
import java.util.regex.Pattern

class QuranAdapter(val quranList: List<Quran>) :
    RecyclerView.Adapter<QuranAdapter.QuranViewHolder>(){

    var itemClickListener: ((Quran) -> Unit)? = null
    var itemClickListener2: ((Quran) -> Unit)? = null
    var shareClickListener: ((Quran) -> Unit)? = null
    var playerClickListener: ((Quran) -> Unit)? = null
    var playAllClickListener: ((List<Quran>) -> Unit)? = null
    var footNotesClickListener: ((Quran) -> Unit)? = null
    var lastReadClickListener: ((Quran) -> Unit)? = null
    var bookmarkClickListener:((Quran) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuranViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tampilan_surah, parent, false)
        return QuranViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuranViewHolder, position: Int) {
        val quran: Quran = quranList[position]
        holder.bindView(quran)
        holder.surahExplanation.isVisible = quran.ayahNumber == 1
        holder.buttonCopy.setOnClickListener {
            itemClickListener2?.invoke(quran)
        }
        holder.itemView.setOnClickListener {
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
        holder.textTranslation.setOnClickListener {
            footNotesClickListener?.invoke(quran)
        }
        holder.btnBookmark.setOnClickListener{
            bookmarkClickListener?.invoke(quran)
        }
    }

    override fun getItemCount(): Int {
        return quranList.size
    }

    class QuranViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        val btnBookmark:ImageButton = itemView.findViewById(R.id.button_bookmark)
        val jumlahAyat: TextView = itemView.findViewById(R.id.text_total_ayah)


        fun bindView(quran: Quran) {
            val context = itemView.context
            textSurahName.text = "${quran.surahNameEnglish} (${quran.surahNameArabic})"

            val ayahQuran = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
                quran.ayahText
            } else {
                reverseAyahNumber(quran.ayahText.toString())
            }

//            textAyah.text = QuranArabicUtils.getTajweed(context, ayahQuran)

            when(Preferance(context).tajweed){
                0 ->{
                    try {
                        textAyah.text = QuranArabicUtils.getTajweed(context, ayahQuran)
                    } catch (e: Exception) {
                        textAyah.text = ayahQuran
                    }
                }
                1 ->{
                    textAyah.text = ayahQuran
                }
            }


            when(Preferance(context).gone){
                0 ->{
                    textTranslation.isVisible = true
                }
                1 ->{
                    textTranslation.isVisible = false
                }
            }

            when(Preferance(context).translation){
                0->{
                    textTranslation.textSize = 20f
                }
                1->{
                    textTranslation.textSize = 30f
                }
                2->{
                    textTranslation.textSize = 40f
                }
            }

            when(Preferance(context).arab){
                0->{
                    textAyah.textSize = 35f
                }
                1->{
                    textAyah.textSize = 40f
                }
                2->{
                    textAyah.textSize = 45f
                }
            }


            createFootnotesSuperscript(textTranslation, quran.translation.toString())

            ayahNumber.text = quran.ayahNumber.toString()

            if (quran.surahNumber == 1 && quran.ayahNumber == 1) {
                pembuka.setText("أَعُوذُ بِاللَّهِ مِنَ الشَّيْطَانِ الرَّجِيمِ")
            } else {
                pembuka.setText("بِسۡمِ ٱللَّهِ ٱلرَّحۡمَٰنِ ٱلرَّحِيم")
            }
        }

        fun createFootnotesSuperscript(textView: TextView ,translation: String){
            val colorPrimary = textView.resources.getColor(R.color.colorPrimary)
            val sb = SpannableStringBuilder(translation)
            val p:Pattern = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE)
            val a:Matcher = p.matcher(translation)
            while (a.find()){
                val clickableSpan = object : ClickableSpan(){
                    override fun updateDrawState(textPaint: TextPaint) {
                        textPaint.color = colorPrimary
                        textPaint.isUnderlineText = true
                        textPaint.textSize = 30F
                    }
                    override fun onClick(p0: View) {

                    }
                }
                sb.setSpan(clickableSpan, a.start(), a.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                sb.setSpan(
                    SuperscriptSpan(),
                    a.start(),
                    a.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            textView.text = sb
        }

        fun reverseAyahNumber(ayahText: String): String {
            val digits = mutableListOf<Char>()
            ayahText.forEach { char ->
                if (char.isDigit()) {
                    digits.add(char)
                }
            }
            val reversDigits = digits.reversed()
            return ayahText.replace(digits.joinToString(""), reversDigits.joinToString(""))
        }
    }

}
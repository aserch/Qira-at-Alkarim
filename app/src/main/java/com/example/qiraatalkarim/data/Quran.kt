package com.example.qiraatalkarim.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quran")
data class Quran(

    @PrimaryKey
    val id: Int? = 0,
    @ColumnInfo(name = "jozz")
    val juzNumber: Int? = 0,
    @ColumnInfo(name = "sora")
    val surahNumber: Int? = 0,
    @ColumnInfo(name = "sora_name_en")
    val surahNameEnglish: String? = "",
    @ColumnInfo(name = "sora_name_ar")
    val surahNameArabic: String? = "",
    @ColumnInfo(name = "page")
    val page: Int? = 0,
    @ColumnInfo(name = "line_start")
    val lineStart: Int? = 0,
    @ColumnInfo(name = "line_end")
    val lineEnd: Int? = 0,
    @ColumnInfo(name = "aya_no")
    val ayahNumber: Int? = 0,
    @ColumnInfo(name = "aya_text")
    val ayahText: String? = "",
    @ColumnInfo(name = "aya_text_emlaey")
    val noHarokahText: String? = "",
    @ColumnInfo(name = "translation")
    val translation: String? = "",
    @ColumnInfo(name = "footnotes")
    val footNotes: String? = ""

)

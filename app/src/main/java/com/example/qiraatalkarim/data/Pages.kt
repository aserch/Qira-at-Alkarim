package com.example.qiraatalkarim.data

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView("SELECT page, sora_name_en, aya_no FROM quran GROUP BY page")
data class Pages(
    val page: Int,
    @ColumnInfo(name = "sora_name_en")
    val surahNameEnglish: String,
    @ColumnInfo(name = "aya_no")
    val ayahNumber: Int
)

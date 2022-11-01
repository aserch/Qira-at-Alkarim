package com.example.qiraatalkarim.data

import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.DatabaseView

@DatabaseView("SELECT sora, sora_name_ar, sora_name_en, count(aya_no) AS total_ayah FROM quran GROUP BY sora")
data class Surah(
    @ColumnInfo(name = "sora")
    val surahNumber: Int,
    @ColumnInfo(name = "sora_name_ar")
    val surahNameArabic: String,
    @ColumnInfo(name = "sora_name_en")
    val surahNameEnglish: String,
    @ColumnInfo(name = "total_ayah")
    val totalSurah: Int
)

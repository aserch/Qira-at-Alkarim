package com.example.qiraatalkarim.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Bookmark")
data class Bookmark(
    @PrimaryKey(autoGenerate = true)
    val id : Int?,
    val surahNameEn: String?,
    val surahNameAr : String?,
    val ayahNumber : Int?,
    val SurahNumber : Int?

)

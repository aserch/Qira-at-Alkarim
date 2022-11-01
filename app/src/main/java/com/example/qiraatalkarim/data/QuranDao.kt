package com.example.qiraatalkarim.data

import kotlinx.coroutines.flow.Flow
import androidx.room.*


@Dao
interface QuranDao {
    @Query("SELECT * FROM surah")
    suspend  fun getSurahList():List<Surah>

    @Query("SELECT * FROM Juzz")
    suspend fun getJuzList():List<Juzz>

    @Query("SELECT * FROM Pages")
    suspend fun getPageList():List<Pages>

    @Query("SELECT * FROM Bookmark")
    fun getBookmark():Flow<List<Bookmark>>

    @Query("SELECT * FROM quran WHERE sora = :surahNumber")
    suspend fun getAyahFromSurah(surahNumber: Int): List<Quran>

    @Query("SELECT * FROM quran WHERE jozz = :juzNumber")
    suspend fun  getAyahFromJuz(juzNumber: Int): List<Quran>

    @Query("SELECT * FROM quran WHERE page = :pageNumber")
    suspend fun getAyahFromPage(pageNumber: Int): List<Quran>

    @Query("SELECT * FROM quran WHERE translation LIKE :search")
    suspend fun searchAyah(search:String): List<Quran>

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Insert
    suspend fun insertBookmark(bookmark: Bookmark)

}
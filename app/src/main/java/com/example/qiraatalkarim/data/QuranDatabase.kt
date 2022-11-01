package com.example.qiraatalkarim.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.qiraatalkarim.R

@Database(version = 2, entities = [Quran::class, Bookmark::class], views = [Surah::class, Pages::class, Juzz::class])
abstract class QuranDatabase : RoomDatabase(){
    abstract fun quranDao() : QuranDao

    companion object{
        @Volatile
        private var INSTANCE: QuranDatabase? = null
        fun getInstance(context: Context): QuranDatabase{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    QuranDatabase::class.java,
                    "quran.db"
                ).createFromInputStream{
                    context.resources.openRawResource(R.raw.quran)
                }.build()
            }
        }
    }
}
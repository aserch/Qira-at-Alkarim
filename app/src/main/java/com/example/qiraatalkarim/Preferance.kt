package com.example.qiraatalkarim

import android.content.Context
import android.preference.PreferenceManager

class Preferance(context: Context) {
    companion object{
        private const val THEME_KEY = "THEME_VALUE"
        private const val QORI_KEY = "QORI_VALUE"
        private const val TEXT_TRANSLATION_KEY ="TRANSLATION_KEY"
        private const val TEXT_ARAB_KEY ="ARAB_KEY"
        private const val TEXT_TRANSLATION_GONE ="TEXT_GONE"
        private const val TAJWEED ="TAJWEED"
    }

    private val recentTheme = PreferenceManager.getDefaultSharedPreferences(context)
    var theme = recentTheme.getString(THEME_KEY,"light")
        set(value) = recentTheme.edit().putString(THEME_KEY, value).apply()

    private  val recentQori = PreferenceManager.getDefaultSharedPreferences(context)
    var qori = recentQori.getInt(QORI_KEY, 0)
        set(value) = recentQori.edit().putInt(QORI_KEY, value).apply()

    private val recentTextSize = PreferenceManager.getDefaultSharedPreferences(context)
    var translation = recentTextSize.getInt(TEXT_TRANSLATION_KEY, 0)
        set(value) = recentQori.edit().putInt(TEXT_TRANSLATION_KEY,value).apply()

    private val recentTextSizearab = PreferenceManager.getDefaultSharedPreferences(context)
    var arab = recentTextSizearab.getInt(TEXT_ARAB_KEY, 0)
        set(value) = recentTextSizearab.edit().putInt(TEXT_ARAB_KEY,value).apply()

    private val translation_gone = PreferenceManager.getDefaultSharedPreferences(context)
    var gone = translation_gone.getInt(TEXT_TRANSLATION_GONE, 1)
        set(value) = translation_gone.edit().putInt(TEXT_TRANSLATION_GONE,value).apply()

    private val tajweed_gone = PreferenceManager.getDefaultSharedPreferences(context)
    var tajweed = tajweed_gone.getInt(TAJWEED, 0)
    set(value) = tajweed_gone.edit().putInt(TAJWEED,value).apply()
}

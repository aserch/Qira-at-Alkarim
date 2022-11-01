package com.example.qiraatalkarim

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class QuranIndexActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quran_index)

        val btnFindMesjid:MaterialCardView = findViewById(R.id.btn_dzikir)
        btnFindMesjid.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.id/maps/search/masjid/@-6.2259177,106.807296,13z/data=!3m1!4b1?hl=id"))
            startActivity(intent)
        }

        val toolbar:MaterialToolbar = findViewById(R.id.toolbar)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.setting ->{
                    startActivity(Intent(this@QuranIndexActivity, SettingActivity::class.java))
                    true
                }
                else ->{
                    false
                }
            }
        }

        val tabLayout = findViewById<TabLayout>(R.id.tablayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewpager)
        val fragmentList = listOf<Fragment>(SurahFragmentActivity(), JuzFragmentActivity(), PageFragmentActivity(), BookmarkFragment())
        val adapter = ViewPagerAdapter(this, fragmentList)

        viewPager.adapter = adapter
        val title = listOf<String>("Surah", "Juz", "Page", "Bookmark")

        TabLayoutMediator(tabLayout, viewPager){tab, position->
            tab.text = title[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    private fun setTheme(){


        when(Preferance(this).theme){
            "light" ->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            "dark" ->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val btnLastRead: MaterialCardView = findViewById(R.id.btn_lastread)
        val textSurahLastRead: TextView = findViewById(R.id.surah_name)
        val textAyahLastRead: TextView = findViewById(R.id.ayah_number)

        val preferance = getSharedPreferences("setting", Context.MODE_PRIVATE)
        val surahName = preferance.getString(SettingActivity.KEY_SURAH_NAME,"")
        val surahNumber = preferance.getInt(SettingActivity.KEY_SURAH_NUMBER,1)
        val ayahNumber = preferance.getInt(SettingActivity.KEY_AYAH_NUMBER,1)

        textSurahLastRead.text = "${surahName}: "
        textAyahLastRead.text = "${ayahNumber}"

        btnLastRead.setOnClickListener {
            val intent = Intent(this@QuranIndexActivity, QuranActivity::class.java)
            intent.putExtra(QuranActivity.SURAH_NUMBER, surahNumber)
            intent.putExtra(QuranActivity.AYAH_NUMBER, ayahNumber)
            startActivity(intent)
        }
    }
}
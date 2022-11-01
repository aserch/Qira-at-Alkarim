package com.example.qiraatalkarim

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.icu.text.Transliterator
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qiraatalkarim.data.Bookmark
import com.example.qiraatalkarim.data.MyPlayerService
import com.example.qiraatalkarim.data.Quran
import com.example.qiraatalkarim.data.QuranDatabase
import com.google.android.material.animation.Positioning
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.l4digital.fastscroll.FastScrollRecyclerView
import kotlinx.coroutines.launch
import snow.player.PlayMode
import snow.player.Player
import snow.player.PlayerClient
import snow.player.audio.MusicItem
import snow.player.playlist.Playlist


class QuranActivity:AppCompatActivity(R.layout.quran) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = QuranDatabase.getInstance(this)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_quran)


        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.search ->{
                    startActivity(Intent(this@QuranActivity, SearchActivity::class.java))
                    true
                }
                R.id.setting ->{
                    val intent = Intent(this@QuranActivity, SettingActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent)
                    true
                }
                else -> false

            }
        }

        val surahNumber: Int = intent.getIntExtra(SURAH_NUMBER, 1)
        val juzNumber = intent.getIntExtra(JUZ_NUMBER,1)
        val pageNumber = intent.getIntExtra(PAGE_NUMBER,1)
        val tabIndex = intent.getIntExtra(TAB_INDEX,0)


        when(tabIndex){
            0 ->{
                lifecycleScope.launch {
                    val quranList = database.quranDao().getAyahFromSurah(surahNumber)
                    setQuranAdapter(quranList)
                    toolbar.setTitle("Surah")
                }
            }

            1 ->{
                lifecycleScope.launch {
                    val quranList = database.quranDao().getAyahFromJuz(juzNumber)
                    setQuranAdapter(quranList)
                    toolbar.setTitle("Juz")
                }
            }
            2 ->{
                lifecycleScope.launch {
                    val quranList = database.quranDao().getAyahFromPage(pageNumber)
                    setQuranAdapter(quranList)
                    toolbar.setTitle("Page")
                }
            }
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }


    }

    fun setQuranAdapter(quranList: List<Quran>){
        val recyclerView:RecyclerView = findViewById(R.id.recyclerview)
        val playerClient = PlayerClient.newInstance(this@QuranActivity, MyPlayerService::class.java)
        val database = QuranDatabase.getInstance(this)
        val tabIndex = intent.getIntExtra(TAB_INDEX,0)
        val adapter = QuranAdapter(quranList)

        recyclerView.adapter = adapter

        adapter.bookmarkClickListener = {
            val bookmark = Bookmark(it.id,it.surahNameEnglish,it.surahNameArabic,it.ayahNumber, it.surahNumber)
            lifecycleScope.launch {
                database.quranDao().insertBookmark(bookmark)
                Toast.makeText(this@QuranActivity, "Berhasil ter bookmark", Toast.LENGTH_SHORT).show()
            }
        }

        val preferences = getSharedPreferences("setting", Context.MODE_PRIVATE)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when(newState){
                    RecyclerView.SCROLL_STATE_IDLE ->{
                        val currentPosition = recyclerView.getCurentPosition()
                        val lastQuranRead = quranList.get(currentPosition)

                        if (lastQuranRead != null) lastQuranRead.let {
                            preferences.edit().putInt(SettingActivity.KEY_SURAH_NUMBER, it.surahNumber!!).apply()
                            preferences.edit().putInt(SettingActivity.KEY_AYAH_NUMBER, it.ayahNumber!!).apply()
                            preferences.edit().putString(SettingActivity.KEY_SURAH_NAME, it.surahNameEnglish).apply()
                        }
                    }
                    RecyclerView.SCROLL_STATE_DRAGGING ->{
                    }
                    RecyclerView.SCROLL_STATE_SETTLING ->{
                    }
                }
            }
        })
//        Buat langsung scroll ke position
        val lastAyahNumber = intent.getIntExtra(AYAH_NUMBER, 1)
//        recyclerView.smoothScrollToPosition(lastAyahNumber - 1)
        lastAyahNumber?.let {
            recyclerView.scrollToPosition(it - 1)
        }

        adapter.footNotesClickListener = {
            val footNotesBottomSheet = FootNotesFragment().apply {
                arguments = bundleOf(
                    FootNotesFragment.EXTRA_FOOTNOTES to it.footNotes,
                    FootNotesFragment.EXTRA_AYAH_NUMBER to it.ayahNumber,
                    FootNotesFragment.EXTRA_SURAH_NAME to it.surahNameEnglish

                )
            }
            footNotesBottomSheet.show(supportFragmentManager, "footNotes")
        }

        playerClient.addOnPlayingMusicItemChangeListener { musicItem, position, playProgress ->
            recyclerView.smoothScrollToPosition(position)
            if(position == quranList.lastIndex){
                playerClient.playMode = PlayMode.SINGLE_ONCE
            }
        }

        adapter.itemClickListener2 ={
            val clipBoard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("TextView", "${it.surahNameArabic}\n${it.ayahText}\n${it.translation}")

            clipBoard.setPrimaryClip(clip)
            Toast.makeText(this@QuranActivity, "Ayah copied to clipboard", Toast.LENGTH_SHORT).show()
        }
        adapter.shareClickListener = {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Surah: ${it.surahNameArabic}\nAyah: ${it.ayahText}\nTranslation: ${it.translation}")
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }

        adapter.playAllClickListener = {  listQuran ->
            val playlist = createPlaylist(listQuran)
            playerClient.connect{
                playerClient.setPlaylist(playlist, true)
                playerClient.playMode = PlayMode.PLAYLIST_LOOP
            }
        }

        val qoriList = resources.getStringArray(R.array.qoriname)
        val qoriListLink = resources.getStringArray(R.array.qoriLink)

        val qoriposition = Preferance(this).qori
        val qoriName = qoriList[qoriposition]
        val qoriLink = qoriListLink[qoriposition]

        adapter.playerClickListener = {
            val gambarMishary = "https://upload.wikimedia.org/wikipedia/commons/2/24/%D0%9C%D0%B8%D1%88%D0%B0%D1%80%D0%B8_%D0%A0%D0%B0%D1%88%D0%B8%D0%B4.jpg"
            val audioUrl = String.format(
                "https://www.everyayah.com/data/$qoriLink/%03d%03d.mp3",
                it.surahNumber,
                it.ayahNumber
            )
            Log.d("AWDIO", audioUrl)
            val audio = MusicItem.Builder()
                .setTitle("${it.surahNameArabic}: ${it.ayahNumber}")
                .setArtist("$qoriName")
                .setAlbum("Quran Recitation")
                .autoDuration()
                .setUri(audioUrl)
                .setIconUri(gambarMishary)
                .build()

            val playList: Playlist = Playlist.Builder()
                .append(audio)
                .build()
            playerClient.connect { success -> // DEBUG
                Log.d("App", "connect: $success")
                playerClient.setPlaylist(playList, true)
                playerClient.playMode = PlayMode.SINGLE_ONCE
            }
        }
    }

    private fun createPlaylist(listAyah: List<Quran>): Playlist {

        val listMurotal = mutableListOf<MusicItem>()

        listAyah.forEach{
            val formatedAyahNumber = String.format("%03d", it.ayahNumber)
            val formatedSurahNumber = String.format("%03d", it.surahNumber)
            val qoriLink = Preferance(this).qori
            val qoriListLink = resources.getStringArray(R.array.qoriLink)
            val linkQori = qoriListLink[qoriLink]

            val murotalItem = MusicItem.Builder()
                .setTitle(it.surahNameEnglish!!)
                .setUri("https://www.everyayah.com/data/$linkQori/$formatedSurahNumber$formatedAyahNumber.mp3")
                .autoDuration()
                .build()

            listMurotal.add(murotalItem)
        }

        return Playlist.Builder()
            .appendAll(listMurotal)
            .build()
    }

    fun RecyclerView.getCurentPosition(): Int{
        return (this.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
    }

    companion object{
        const val SURAH_NUMBER = "surah_number"
        const val AYAH_NUMBER = "ayah_number"
        const val TAB_INDEX = "tab_index"
        const val JUZ_NUMBER = "juz_number"
        const val PAGE_NUMBER = "page_number"
    }
}
package com.example.qiraatalkarim

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import java.util.prefs.Preferences

class SettingActivity : AppCompatActivity(R.layout.setting) {

    private lateinit var switchTheme:Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme()

//        val actQori: AutoCompleteTextView = findViewById(R.id.qori_selection)
//
//        val items = listOf("Alafasy_128kbps", "AbdulSamad_64kbps_QuranExplorer.Com", "Abdurrahmaan_As-Sudais_192kbp", "Hudhaify_64kbps/")
//        val adapter = ArrayAdapter(this, R.layout.list_item_menu, items)
//        actQori.setAdapter(adapter)
//
//        val sharedPreferance = getSharedPreferences("setting", Context.MODE_PRIVATE)
//        sharedPreferance.getInt("setting", 0)


        val toolbar:MaterialToolbar = findViewById(R.id.toolbar_setting)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val tombolQori:MaterialCardView = findViewById(R.id.qori_selection)
        val currentQori = findViewById<TextView>(R.id.Qori)

        val qoriName = resources.getStringArray(R.array.qoriname)
        tombolQori.setOnClickListener{
            MaterialAlertDialogBuilder(this@SettingActivity, R.style.MaterialThemeDialog)
                .setTitle("Pilih Qori")
                .setSingleChoiceItems(qoriName,
                    Preferance(this@SettingActivity).qori) {dialog:DialogInterface,position:Int ->
                    Preferance(this).qori = position
                    currentQori.setText("Qori saat ini:" + qoriName[position])
                    dialog.dismiss()
                }.create().show()
        }

        val btnChanceSize: MaterialCardView = findViewById(R.id.font_selection)
        val currentSize: TextView = findViewById(R.id.text_size)

        btnChanceSize.setOnClickListener {
            MaterialAlertDialogBuilder(this@SettingActivity, R.style.MaterialThemeDialog)
                .setTitle("Size")
                .setSingleChoiceItems(arrayOf("Small", "Medium", "Big"), Preferance(this).translation){dialog:DialogInterface, wich:Int ->
                    when(wich){
                        0->{
                            Preferance(this).translation = 0
                            currentSize.setText("Small")
                            dialog.dismiss()
                        }
                        1->{
                            Preferance(this).translation = 1
                            currentSize.setText("Medium")
                            dialog.dismiss()
                        }
                        2->{
                            Preferance(this).translation = 2
                            currentSize.setText("Big")
                            dialog.dismiss()
                        }
                    }
                }.create().show()
        }
        val tombol = intent.getStringExtra("")
        val btnChanceSizeArab: MaterialCardView = findViewById(R.id.font_arab_selection)
        val currentSizeArab:TextView = findViewById(R.id.text_size_arab)

        btnChanceSizeArab.setOnClickListener {
            MaterialAlertDialogBuilder(this@SettingActivity, R.style.MaterialThemeDialog)
                .setTitle("Size")
                .setSingleChoiceItems(arrayOf("Small", "Medium", "Big"),
                    Preferance(this).arab){dialog:DialogInterface, wich->
                    when(wich){
                        0->{
                            Preferance(this).arab = 0
                            currentSizeArab.setText("Small")
                            dialog.dismiss()
                        }
                        1->{
                            Preferance(this).arab = 1
                            currentSizeArab.setText("Medium")
                            dialog.dismiss()
                        }
                        2->{
                            Preferance(this).arab = 2
                            currentSizeArab.setText("Big")
                            dialog.dismiss()
                        }
                    }
                }.create().show()
        }

        val switchTranslation:Switch = findViewById(R.id.switch_translation)
        switchTranslation.setOnCheckedChangeListener { compoundButton, b ->
            when(b){
                true->{
                    Preferance(this).gone = 1
                }
                false ->{
                    Preferance(this).gone = 0
                }
            }
        }

        val switchTajweed = findViewById<Switch>(R.id.switch_tajweed)
        switchTajweed.setOnCheckedChangeListener { compoundButton, b ->
            when(b){
                true ->{
                    Preferance(this).tajweed = 1
                }
                false ->{
                    Preferance(this).tajweed = 0
                }
            }
        }

        switchTheme = findViewById(R.id.switch_theme)!!
        switchTheme.setOnCheckedChangeListener { view, isCheked ->

            when (isCheked){
                true ->{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    Preferance(this).theme = "dark"
                }
                false ->{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    Preferance(this).theme = "light"
                }
            }
        }
    }


    private fun setTheme(){
        switchTheme = findViewById(R.id.switch_theme)!!
        val switchTranslation:Switch = findViewById(R.id.switch_translation)
        val currentText:TextView = findViewById(R.id.text_size)
        val currentQori = findViewById<TextView>(R.id.Qori)
        val currentSizeArab:TextView = findViewById(R.id.text_size_arab)

        when(Preferance(this).gone){
            0->{
                switchTranslation.isChecked = false
            }
            1->{
                switchTranslation.isChecked = true
            }
        }

        val switchTajweed = findViewById<Switch>(R.id.switch_tajweed)
        when(Preferance(this).tajweed){
            0 ->{
                switchTajweed.isChecked = false
            }
            1 ->{
                switchTajweed.isChecked = true
            }
        }

        when(Preferance(this).theme){
            "light" ->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
            "dark" ->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            }
        }

        val qoriName = resources.getStringArray(R.array.qoriname)
        val setQori = Preferance(this).qori
        currentQori.setText("Qori saat ini:" + qoriName[setQori])

        when(Preferance(this).translation){
            0 ->{
                currentText.setText("Small")
            }
            1->{
                currentText.setText("Medium")
            }
            2->{
                currentText.setText("Big")
            }
        }
        when(Preferance(this).arab){
            0 ->{
                currentSizeArab.setText("Small")
            }
            1->{
                currentSizeArab.setText("Medium")
            }
            2->{
                currentSizeArab.setText("Big")
            }

        }
    }

    companion object{
        const val KEY_SURAH_NUMBER ="surah_number"
        const val KEY_AYAH_NUMBER ="ayah_number"
        const val KEY_SURAH_NAME ="surah_name"
    }
}
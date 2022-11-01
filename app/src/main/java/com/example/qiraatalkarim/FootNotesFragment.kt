package com.example.qiraatalkarim

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FootNotesFragment:BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottomsheet_footnotes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: MaterialToolbar = view.findViewById(R.id.toolbar_footnote)
        val note: TextView = view.findViewById(R.id.text)

        val footNote = arguments?.getString(EXTRA_FOOTNOTES)
        val surahName = arguments?.getString(EXTRA_SURAH_NAME)
        val ayahNumber = arguments?.getInt(EXTRA_AYAH_NUMBER)

        toolbar.subtitle = "${surahName}: ${ayahNumber}"
        note.text = footNote.toString().replace("\n", "\n\n")

        toolbar.setOnMenuItemClickListener {
            if(it.itemId == R.id.close){
                dismiss()
            }
            true
        }

    }

    companion object{
        const val EXTRA_FOOTNOTES = "footnote"
        const val EXTRA_SURAH_NAME = "surah_name"
        const val  EXTRA_AYAH_NUMBER = "aya_no"
    }
}
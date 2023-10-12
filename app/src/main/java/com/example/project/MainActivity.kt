package com.example.project

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.preference.PreferenceManager
import com.example.example.ActsDbHelper
import com.example.project.databinding.ActivityMainBinding
import com.example.project.fragments.TimetableFragment1
import com.example.project.fragments.TimetableFragment2
import com.example.project.fragments.TimetableFragment3
import com.example.project.fragments.TimetableFragment4
import com.example.project.fragments.TimetableFragment5
import com.example.project.fragments.TimetableFragment6
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var changeParity = true
    private lateinit var defPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbarMain))
        supportActionBar?.title = ""

        defPref = PreferenceManager.getDefaultSharedPreferences(this)

        getData()
        makeFragTimetable()
        makeSidebar()
    }

    override fun onResume() {
        super.onResume()
        getData()
        makeFragTimetable()
        makeSidebar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    private fun makeSidebar() {
        val layout = binding.linerLayout
        layout.removeAllViews()
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(20, 0, 0, 0)
        var subjects = arrayOf<String>()
        when (Storage.dbName) {
            "timetable_bvt2201_db" -> subjects = Storage.subjectsBVT2201
            "timetable_bvt2202_db" -> subjects = Storage.subjectsBVT2202
            "timetable_bvt2203_db" -> subjects = Storage.subjectsBVT2203
            "timetable_bvt2204_db" -> subjects = Storage.subjectsBVT2204
        }

        if (subjects.isNotEmpty()) {
            for (btnText in subjects) {
                val btn = Button(this)
                btn.text = btnText
                btn.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                btn.setBackgroundColor(getColor(R.color.transperent))

                btn.setOnClickListener {
                    val intent = Intent(this, NoteActivity::class.java)
                    startActivity(intent)
                    Storage.subject = btn.text.toString()
                }

                layout.addView(btn, params)
            }
        }
    }

    private fun makeFragTimetable () {
        val fragList = listOf(
            TimetableFragment1.newInstance(),
            TimetableFragment2.newInstance(),
            TimetableFragment3.newInstance(),
            TimetableFragment4.newInstance(),
            TimetableFragment5.newInstance(),
            TimetableFragment6.newInstance())

        val adapter = VpAdapter(this, fragList)
        val fragTitles = listOf(
            getString(R.string.tab_1),
            getString(R.string.tab_2),
            getString(R.string.tab_3),
            getString(R.string.tab_4),
            getString(R.string.tab_5),
            getString(R.string.tab_6)
        )
        binding.TimetableVP2.adapter = adapter
        TabLayoutMediator(binding.timetableTabLayout, binding.TimetableVP2) {
                tab, pos -> tab.text = fragTitles[pos]
        }.attach()
    }

    private fun getData () {
        val dbName: String = defPref.getString("group_number", "timetable_bvt2201_db").toString()
        val myDatabase = ActsDbHelper(this, dbName).readableDatabase
        val cursor = myDatabase.rawQuery("SELECT * FROM timetable", null)
        Storage.dbName = dbName
        Storage.cursor = cursor
    }

    fun onClickParity (view: View) {
        changeParity = !changeParity
        if (changeParity) {
            binding.buttonParity.text = getString(R.string.parity_odd)
            Storage.parity = getString(R.string.parity_odd)
            makeFragTimetable()
        }
        else {
            binding.buttonParity.text = getString(R.string.parity_even)
            Storage.parity = getString(R.string.parity_even)
            makeFragTimetable()
        }
    }
}
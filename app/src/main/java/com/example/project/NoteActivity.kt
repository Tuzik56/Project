package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.view.Menu
import android.view.MenuItem
import com.example.example.ActsDbHelper
import com.example.project.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbarNote))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.tvSubjectTitle.text = Storage.subject
    }

    override fun onResume() {
        super.onResume()
        val db = DbHelper(this).writableDatabase
        val subject = Storage.subject
        val cursor = db.rawQuery("SELECT * FROM notes WHERE subject = '$subject'", null)
        cursor.moveToFirst()
        binding.editText.setText(cursor.getString(1))
    }

    override fun onPause() {
        super.onPause()
        val subject = Storage.subject
        val note = binding.editText.text.toString()
        val db = DbHelper(this).writableDatabase
        val query = "UPDATE notes SET note = '$note' WHERE subject = '$subject';"
        db.execSQL(query)
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
            android.R.id.home -> finish()
        }
        return true
    }
}
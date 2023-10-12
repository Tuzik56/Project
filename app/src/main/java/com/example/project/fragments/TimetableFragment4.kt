package com.example.project.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project.Storage
import com.example.project.databinding.FragmentTimetable4Binding

class TimetableFragment4: Fragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTimetable4Binding.inflate(inflater)
        val weekday = "Четверг"
        val parity = Storage.parity
        val cursor = Storage.cursor
        cursor.moveToFirst()

        while (!cursor.isAfterLast) {
            if (cursor.getString(1) == parity) {
                if (cursor.getString(2) == weekday) {
                    when (cursor.getString(3)) {
                        "09:30 - 11:05" -> {
                            binding.textView1.text =
                                cursor.getString(3) + "\n" +
                                        cursor.getString(4) + "\n" +
                                        cursor.getString(5) + "\n" +
                                        cursor.getString(6)
                            binding.textViewRoom1.text = cursor.getString(7)
                        }
                        "11:20 - 12:55" -> {
                            binding.textView2.text =
                                cursor.getString(3) + "\n" +
                                        cursor.getString(4) + "\n" +
                                        cursor.getString(5) + "\n" +
                                        cursor.getString(6)
                            binding.textViewRoom2.text = cursor.getString(7)
                        }
                        "13:10 - 14:45" -> {
                            binding.textView3.text =
                                cursor.getString(3) + "\n" +
                                        cursor.getString(4) + "\n" +
                                        cursor.getString(5) + "\n" +
                                        cursor.getString(6)
                            binding.textViewRoom3.text = cursor.getString(7)
                        }
                        "15:25 - 17:00" -> {
                            binding.textView4.text =
                                cursor.getString(3) + "\n" +
                                        cursor.getString(4) + "\n" +
                                        cursor.getString(5) + "\n" +
                                        cursor.getString(6)
                            binding.textViewRoom4.text = cursor.getString(7)
                        }
                        "17:15 - 18:50" -> {
                            binding.textView5.text =
                                cursor.getString(3) + "\n" +
                                        cursor.getString(4) + "\n" +
                                        cursor.getString(5) + "\n" +
                                        cursor.getString(6)
                            binding.textViewRoom5.text = cursor.getString(7)
                        }
                    }
                }
            }
            cursor.moveToNext()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = TimetableFragment4()
    }
}
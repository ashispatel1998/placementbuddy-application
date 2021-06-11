package com.example.prism.dev.ashis.patel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner

class UserRegistration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)

        val degreeSpinner = findViewById<AutoCompleteTextView>(R.id.degree)
        val degreeOption = resources.getStringArray(R.array.qualification_array)
        val adapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, degreeOption)
        degreeSpinner.setAdapter(adapter)

        val passoutYearSpinner= findViewById<AutoCompleteTextView>(R.id.passout_year)
        val passoutOption=resources.getStringArray(R.array.passout_year_array)
        val adapter2 = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, passoutOption)
        passoutYearSpinner.setAdapter(adapter2)

    }

}
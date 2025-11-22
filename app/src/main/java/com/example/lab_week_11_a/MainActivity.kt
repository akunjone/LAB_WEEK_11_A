package com.example.lab_week_11_a

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.widget.TextView
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ini klo pake SharedPreferences:
        //val preferenceWrapper = (application as PreferenceApplication).preferenceWrapper

        //ini kalo pake DataStore:
        val preferenceWrapper = (application as SettingsApplication).settingsStore

        val preferenceViewModel = ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                //ini klo pake SharedPreferences:
                //return PreferenceViewModel(preferenceWrapper) as T

                //ini klo pake DataStore:
                return SettingsViewModel(preferenceWrapper) as T
            }
            //ini klo pake SharedPreferences:
            //})[PreferenceViewModel::class.java]
        //ini klo pake DataStore:
        })[SettingsViewModel::class.java]

        //ini klo pake SharedPreferences:
        //preferenceViewModel.getText().observe(this

        //ini klo pake DataStore:
        preferenceViewModel.textLiveData.observe(this
        ){
            findViewById<TextView>(
                R.id.activity_main_text_view
            ).text = it
        }
        findViewById<Button>(R.id.activity_main_button).setOnClickListener{
            preferenceViewModel.saveText(
                findViewById<EditText>(R.id.activity_main_edit_text)
                    .text.toString()
            )
        }
    }
}
package com.example.watchera

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.watchera.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load current mode
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        binding.darkModeSwitch.isChecked = nightMode == AppCompatDelegate.MODE_NIGHT_YES

        // Toggle switch
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            val mode = if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO

            AppCompatDelegate.setDefaultNightMode(mode)
            recreate()
        }
    }
}
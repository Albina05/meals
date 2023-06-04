package com.example.mealsApp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.mealsApp.R
import com.example.mealsApp.databinding.ActivityMainBinding

import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val bottomNavigationView = binding?.bottomNavigationMainActivity
        val navController = Navigation.findNavController(this@MainActivity, R.id.fragment_host_main_activity)
        NavigationUI.setupWithNavController(bottomNavigationView as  BottomNavigationView,navController)

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
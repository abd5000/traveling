package com.example.myadmin.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myadmin.R
import com.example.myadmin.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}
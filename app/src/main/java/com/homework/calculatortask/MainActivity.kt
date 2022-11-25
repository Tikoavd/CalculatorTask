package com.homework.calculatortask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.homework.calculatortask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firstFragment = FirstFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.top, firstFragment, firstFragment.tag)
            .commit()
    }
}
package com.wceng.poems

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.wceng.poems.databinding.ActivityMainBinding
import com.wceng.poems.logic.PoemDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val poemDao = PoemDB.getInstance(PoemApp.context).getPoemDao()
        lifecycleScope.launch (){
            val queryAllPoem = poemDao.queryAllPoem()
            Log.d("MainActivity", "onCreate:${Thread.currentThread()} ")
        }

    }
}
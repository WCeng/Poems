package com.wceng.poems.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.ViewModelProvider
import com.wceng.poems.R
import com.wceng.poems.databinding.ActivityMainBinding
import com.wceng.poems.ui.label.LabelActivity
import com.wceng.poems.ui.list.PoemListActivity
import com.wceng.poems.ui.poet.PoetActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setSupportActionBar(binding.mainToolbar)
        setContentView(binding.root)
        initDrawLayout()

        binding.collectionCardView.setOnClickListener {
            PoemListActivity.actionSearchResult(
                this@MainActivity,
                resources.getString(R.string.my_collected_poems),
                PoemListActivity.ACTION_COLLECTED
            )
        }
    }

    private fun initDrawLayout() {
        binding.navigation.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.poet -> {
                    startActivity(Intent(this@MainActivity, PoetActivity::class.java))
                    return@setNavigationItemSelectedListener true
                }

                R.id.label -> {
                    startActivity(Intent(this@MainActivity, LabelActivity::class.java))
                    return@setNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menu?.let {
            val searchView = it.findItem(R.id.mainSearch).actionView as SearchView
            initSearchView(searchView)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                binding.drawLayout.open()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    PoemListActivity.actionSearchResult(
                        this@MainActivity,
                        it,
                        PoemListActivity.ACTION_LIKE
                    )
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }


}
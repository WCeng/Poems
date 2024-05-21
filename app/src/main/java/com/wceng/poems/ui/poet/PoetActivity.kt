package com.wceng.poems.ui.poet

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wceng.poems.R
import com.wceng.poems.databinding.ActivityPoetBinding

class PoetActivity : AppCompatActivity() {

    companion object {
        private val TAG
            get() = PoetActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityPoetBinding
    private val viewModel by lazy {
        ViewModelProvider(this, PoetViewModelFactory()).get(PoetViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoetBinding.inflate(layoutInflater)
        setSupportActionBar(binding.poetToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }
        setContentView(binding.root)

        val adapter = PoetAdapter()
        binding.poetRv.layoutManager = LinearLayoutManager(this)
        binding.poetRv.adapter = adapter

        viewModel.poetLiveData.observe(this) { poets ->
            adapter.showData(poets)
        }

        viewModel.queryPoetLike("")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.poet_menu, menu)
        menu?.let {
            val actionView = it.findItem(R.id.poetSearch).actionView as SearchView
            initSearchView(actionView)
        }
        return true
    }

    private fun initSearchView(actionView: SearchView) {
        actionView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.queryPoetLike(newText ?: "")
                return true
            }

        })
    }
}
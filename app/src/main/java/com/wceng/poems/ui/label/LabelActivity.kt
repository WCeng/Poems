package com.wceng.poems.ui.label

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.ViewModelProvider
import com.wceng.poems.R
import com.wceng.poems.databinding.ActivityLabelBinding
import com.wceng.poems.ui.list.PoemListActivity

class LabelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLabelBinding
    private val viewModel by lazy {
        ViewModelProvider(this, LabelViewModelFactory())[LabelViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLabelBinding.inflate(layoutInflater)
        setSupportActionBar(binding.labelToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)

        val labels = ArrayList<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, labels)
        binding.labelList.adapter = adapter
        viewModel.labelLiveData.observe(this) {
            labels.clear()
            labels.addAll(it)
            adapter.notifyDataSetChanged()
        }

        viewModel.queryLabelLike("")

        binding.labelList.setOnItemClickListener { _, _, position, _ ->
            val s = labels[position]
            PoemListActivity.actionSearchResult(
                this@LabelActivity,
                s,
                PoemListActivity.ACTION_LABEL
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.label_menu, menu)
        menu?.let {
            val searchView = menu.findItem(R.id.labelSearch).actionView as SearchView
            initSearchView(searchView)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.queryLabelLike(newText ?: "")
                return true
            }
        })
    }
}
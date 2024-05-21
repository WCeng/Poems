package com.wceng.poems.ui.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.wceng.poems.R
import com.wceng.poems.databinding.ActivityPoemListBinding
import kotlinx.coroutines.launch

class PoemListActivity : AppCompatActivity() {

    companion object {
        private val TAG = PoemListActivity::class.java.simpleName

        const val ACTION_TITLE = "action_title"
        const val ACTION_POET = "action_poet"
        const val ACTION_LIKE = "action_all"
        const val ACTION_LABEL = "action_label"
        const val ACTION_COLLECTED = "action_collected"

        fun actionSearchResult(context: Context, query: String, actionType: String) {
            val intent = Intent(context, PoemListActivity::class.java)
            intent.putExtra("query", query)
            intent.putExtra("actionType", actionType)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityPoemListBinding

    private val viewModel by lazy {
        ViewModelProvider(this, PoemListViewModelFactory())[PoemListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoemListBinding.inflate(layoutInflater)
        setSupportActionBar(binding.poemListToolbar)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val query = intent.getStringExtra("query")!!
        val actionType = intent.getStringExtra("actionType")!!

        binding.poemListRv.layoutManager = LinearLayoutManager(this)
        val poemAdapter = PoemListAdapter()
        binding.poemListRv.adapter = poemAdapter
        supportActionBar?.title = query

        poemAdapter.addLoadStateListener { states ->
            when (states.refresh) {
                is LoadState.NotLoading -> {
                    binding.poemListRv.visibility = View.VISIBLE
                    binding.poemListPb.visibility = View.GONE
                }

                is LoadState.Loading -> {
                    binding.poemListRv.visibility = View.GONE
                    binding.poemListPb.visibility = View.VISIBLE
                }

                is LoadState.Error -> {
                    binding.poemListPb.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.load_error), Toast.LENGTH_SHORT).show()
                }
            }

        }

        lifecycleScope.launch {
            viewModel.queryPoems(query, actionType).collect { poemData ->
                poemAdapter.submitData(poemData)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
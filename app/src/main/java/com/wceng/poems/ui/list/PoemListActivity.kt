package com.wceng.poems.ui.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
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

        fun actionSearchResult(context: Context, query: String, actionType: String) {
            val intent = Intent(context, PoemListActivity::class.java)
            intent.putExtra("query", query)
            intent.putExtra("actionType", actionType)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityPoemListBinding

    private val viewModel by lazy {
        ViewModelProvider(this, PoemListViewModelFactory())[PoemListViewModel::class]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoemListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val query = intent.getStringExtra("query")!!
        val actionType = intent.getStringExtra("actionType")!!

        binding.poemListRv.layoutManager = LinearLayoutManager(this)
        val poemAdapter = PoemListAdapter()
        binding.poemListRv.adapter = poemAdapter
        binding.poemListToolbar.title = getString(R.string.search_result, query)

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

}
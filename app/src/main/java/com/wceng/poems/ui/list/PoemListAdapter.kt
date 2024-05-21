package com.wceng.poems.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wceng.poems.databinding.PoemItemBinding
import com.wceng.poems.logic.model.Poem
import com.wceng.poems.ui.detail.PoemDetailActivity

class PoemListAdapter : PagingDataAdapter<Poem, PoemListAdapter.PoemViewHolder>(COMPARATOR) {

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Poem>() {
            override fun areItemsTheSame(oldItem: Poem, newItem: Poem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Poem, newItem: Poem): Boolean {
                return oldItem == newItem
            }

        }
    }

    class PoemViewHolder(binding: PoemItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val poemTitle = binding.poemTitleTv
        val poemDynastyPoet = binding.poemDynastyPoetTv
        val poemContent = binding.poemContentTv

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PoemViewHolder, position: Int) {
        val poem = getItem(position)
        poem?.let {
            holder.poemTitle.text = poem.title
            holder.poemDynastyPoet.text = "${poem.dynasty} · ${poem.poet}"
            holder.poemContent.text = poem.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoemViewHolder {
        val binding = PoemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = PoemViewHolder(binding)
        binding.root.setOnClickListener {
            val poem = getItem(viewHolder.bindingAdapterPosition)
            poem?.let {
                PoemDetailActivity.actionShowDetail(parent.context, poem.id, poem.poet)
            }
        }
        return viewHolder
    }
}
package com.wceng.poems.ui.poet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wceng.poems.R
import com.wceng.poems.databinding.PoetItemBinding
import com.wceng.poems.logic.model.Poet
import com.wceng.poems.ui.list.PoemListActivity

class PoetAdapter : RecyclerView.Adapter<PoetAdapter.PoetViewHolder>() {

    private val poets = ArrayList<Poet>()

    fun showData(poets: List<Poet>) {
        val poetDifferCallback = PoetDifferCallback(this.poets, poets)
        val diffResult = DiffUtil.calculateDiff(poetDifferCallback)

        this.poets.clear()
        this.poets.addAll(poets)

        diffResult.dispatchUpdatesTo(this)
    }

    inner class PoetViewHolder(val binding: PoetItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoetViewHolder {
        val binding = PoetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = PoetViewHolder(binding)
        holder.itemView.setOnClickListener {
            val poet = poets[holder.bindingAdapterPosition]
            PoemListActivity.actionSearchResult(
                parent.context, poet.name, PoemListActivity.ACTION_POET
            )
        }
        return holder
    }

    override fun getItemCount(): Int {
        return poets.size
    }

    override fun onBindViewHolder(holder: PoetViewHolder, position: Int) {
        val poet = poets[position]
        holder.binding.poetName.text = poet.name
        holder.binding.poetDynasty.text = poet.dynasty
        Glide.with(holder.itemView.context).load(poet.imgUrl)
            .error(R.drawable.baseline_broken_image_24).into(holder.binding.circleImageView)
    }

    inner class PoetDifferCallback(
        private val oldData: List<Poet>, private val newData: List<Poet>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldData.size
        }

        override fun getNewListSize(): Int {
            return newData.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].id == newData[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition] == newData[newItemPosition]
        }

    }


}
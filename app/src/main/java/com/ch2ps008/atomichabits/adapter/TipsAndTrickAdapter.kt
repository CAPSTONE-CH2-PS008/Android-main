package com.ch2ps008.atomichabits.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps008.atomichabits.databinding.TipsItemBinding
import com.ch2ps008.atomichabits.source.TipsAndTrick
import com.ch2ps008.atomichabits.ui.tipsandtrick.DetailTipsActivity
import com.ch2ps008.atomichabits.util.loadImage

class TipsAndTrickAdapter :
    PagingDataAdapter<TipsAndTrick, TipsAndTrickAdapter.ViewHolder>(DIFF_CALLBACK) {

    private var tipsList: List<TipsAndTrick> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TipsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = tipsList.size

    class ViewHolder(private val binding: TipsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tips: TipsAndTrick) {
            binding.apply {
                tvYourActivity.text = tips.title
                loadImage(root.context, tips.photoUrl, ivTips)
                root.setOnClickListener {
                    val detailIntent = Intent(binding.root.context, DetailTipsActivity::class.java)
                    detailIntent.putExtra(DetailTipsActivity.DETAIL_TIPS, tips)
                    itemView.context.startActivity(
                        detailIntent,
                        ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity)
                            .toBundle()
                    )
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tips = tipsList[position]
        holder.bind(tips)
    }

    fun updateData(newTipsList: List<TipsAndTrick>) {
        tipsList = newTipsList
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TipsAndTrick>() {
            override fun areItemsTheSame(oldItem: TipsAndTrick, newItem: TipsAndTrick): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: TipsAndTrick,
                newItem: TipsAndTrick
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}

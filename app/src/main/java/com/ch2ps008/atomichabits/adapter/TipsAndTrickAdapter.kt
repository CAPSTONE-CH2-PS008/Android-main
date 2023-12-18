package com.ch2ps008.atomichabits.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ch2ps008.atomichabits.databinding.TipsItemBinding
import com.ch2ps008.atomichabits.source.TipsAndTrick

class TipsAndTrickAdapter(private val tipsList: List<TipsAndTrick>) :
    RecyclerView.Adapter<TipsAndTrickAdapter.TipsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipsViewHolder {
        val binding = TipsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TipsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TipsViewHolder, position: Int) {
        val tips = tipsList[position]
        holder.bind(tips)
    }

    override fun getItemCount(): Int = tipsList.size

    inner class TipsViewHolder(private val binding: TipsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tips: TipsAndTrick) {
            Glide.with(binding.root.context)
                .load(tips.photoUrl)
                .centerCrop()
                .into(binding.ivTips)

            binding.tvYourActivity.text = tips.title
        }
    }
}
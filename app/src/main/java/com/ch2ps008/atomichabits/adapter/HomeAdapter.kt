package com.ch2ps008.atomichabits.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps008.atomichabits.databinding.HabitCardviewBinding
import com.ch2ps008.atomichabits.db.Habit
import com.ch2ps008.atomichabits.util.formatHour
import kotlin.math.abs

class HomeAdapter(private val onItemClick: (Habit) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var habits: List<Habit> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HabitCardviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val habit = habits[position]
        holder.bind(habit)
    }

    override fun getItemCount(): Int = habits.size

    fun submitList(newHabits: List<Habit>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return habits[oldItemPosition].id == newHabits[newItemPosition].id
            }

            override fun getOldListSize(): Int = habits.size

            override fun getNewListSize(): Int = newHabits.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return habits[oldItemPosition] == newHabits[newItemPosition]
            }
        })

        habits = newHabits
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: HabitCardviewBinding, private val onItemClick: (Habit) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(habit: Habit) {
            binding.apply {
                tvActivity.text = habit.activityName

                val duration = abs(habit.endHour - habit.startHour)

                val formattedDuration = "$duration"
                tvHour.text = formattedDuration

                itemView.setOnClickListener { onItemClick(habit) }
            }
        }
    }
}
package com.ch2ps008.atomichabits.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps008.atomichabits.databinding.HabitCardviewBinding
import com.ch2ps008.atomichabits.db.Habit

class HomeAdapter :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var habits: List<Habit> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HabitCardviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
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

    class ViewHolder(private val binding: HabitCardviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(habit: Habit) {
            binding.apply {
                tvActivity.text = habit.activityName

                // Ensure that end time is greater than start time
                val actualEndHour = if (habit.endHour < habit.startHour) {
                    habit.endHour + 24
                } else {
                    habit.endHour
                }

                val duration = actualEndHour - habit.startHour

                val formattedDuration = "$duration"
                tvHour.text = formattedDuration
            }
        }
    }
}
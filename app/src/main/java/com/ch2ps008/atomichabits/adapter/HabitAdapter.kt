package com.ch2ps008.atomichabits.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps008.atomichabits.databinding.HabitItemBinding
import com.ch2ps008.atomichabits.db.Habit
import com.ch2ps008.atomichabits.db.HabitDao
import com.ch2ps008.atomichabits.util.formatHour
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HabitAdapter (private val habitDao: HabitDao):
    RecyclerView.Adapter<HabitAdapter.ViewHolder>() {

    private var habits: List<Habit> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HabitItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, habitDao)
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

    class ViewHolder(private val binding: HabitItemBinding, private val habitDao: HabitDao) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var getHabit: Habit

        init {
            binding.btnDelete.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    habitDao.deleteHabit(getHabit)
                }
            }
        }

        fun bind(habit: Habit) {
            getHabit = habit
            binding.apply {
                tvYourActivity.text = habit.activityName
                val startTime = formatHour(habit.startHour)
                val endTime = formatHour(habit.endHour)
                tvTime.text = String.format("%s-%s", startTime, endTime)

                val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val creationDate = sdf.format(Date(habit.creationDate))
                tvDate.text = creationDate
            }
        }
    }
}
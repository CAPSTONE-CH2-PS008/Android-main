package com.ch2ps008.atomichabits.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps008.atomichabits.databinding.HabitItemBinding
import com.ch2ps008.atomichabits.db.Habit
import com.ch2ps008.atomichabits.db.Predict
import com.ch2ps008.atomichabits.util.formatHour
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PredictAdapter(private val predict: List<Predict>) :
    RecyclerView.Adapter<PredictAdapter.ViewHolder>() {

    private var habits: List<Predict> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HabitItemBinding.inflate(
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

    fun getPredictAt(position: Int): Predict {
        return habits[position]
    }

    fun submitList(newHabits: List<Predict>) {
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

    class ViewHolder(private val binding: HabitItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var getHabit: Predict

        fun bind(habit: Predict) {
            getHabit = habit
            binding.apply {
                tvYourActivity.text = habit.result
//                val startTime = formatHour(habit.startHour)
//                val endTime = formatHour(habit.endHour)
//                tvTime.text = String.format("%s-%s", startTime, endTime)
//
//                val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
//                val creationDate = sdf.format(Date(habit.creationDate))
//                tvDate.text = creationDate

                itemView.setOnClickListener {  }
            }
        }
    }
}
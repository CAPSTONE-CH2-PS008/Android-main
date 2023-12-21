package com.ch2ps008.atomichabits.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps008.atomichabits.databinding.PredictItemBinding
import com.ch2ps008.atomichabits.db.HabitDao
import com.ch2ps008.atomichabits.db.Predict
import com.ch2ps008.atomichabits.db.PredictDao
import com.ch2ps008.atomichabits.util.formatHour
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PredictAdapter (private val habitDao: HabitDao, private val predictDao: PredictDao) :
    RecyclerView.Adapter<PredictAdapter.ViewHolder>() {

    private var predict: List<Predict> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PredictItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, habitDao, predictDao)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val habit = predict[position]
        holder.bind(habit)
    }

    override fun getItemCount(): Int = predict.size

    fun submitList(newpredict: List<Predict>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return predict[oldItemPosition].id == newpredict[newItemPosition].id
            }

            override fun getOldListSize(): Int = predict.size

            override fun getNewListSize(): Int = newpredict.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return predict[oldItemPosition] == newpredict[newItemPosition]
            }
        })

        predict = newpredict
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: PredictItemBinding, private val habitDao: HabitDao, private val predictDao: PredictDao) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var getPredict: Predict

        init {
            binding.btnDelete.setOnClickListener {
                showDeleteConfirmationDialog()
            }
        }

        private fun showDeleteConfirmationDialog() {
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle("Confirmation")
            builder.setMessage("Do you want to delete this activity?")

            builder.setPositiveButton("Yes") { _, _ ->
                CoroutineScope(Dispatchers.Main).launch {
                    val predictActivityName = getPredict.activityName
                    val habit = habitDao.getHabitsByActivityName(predictActivityName)
                    habit.forEach { habit ->
                        habitDao.deleteHabit(habit)
                    }
                    predictDao.deletePredict(getPredict)
                }
            }

            builder.setNegativeButton("No") { _, _ ->
            }

            val dialog = builder.create()
            dialog.show()
        }
        
        fun bind(predict: Predict) {
            getPredict = predict
            binding.apply {
                tvYourActivity.text = predict.activityName
                tvYourActivity.setTextColor(getColorForResult(predict.result))
                val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val creationDate = sdf.format(Date(predict.creationDate))
                tvDate.text = creationDate
                val startTime = formatHour(predict.startHour)
                val endTime = formatHour(predict.endHour)
                tvTime.text = String.format("%s-%s", startTime, endTime)
            }
        }

        private fun getColorForResult(result: Int): Int {
            return when (result) {
                1 -> Color.parseColor("#ff0000")
                2 -> Color.parseColor("#ff8300")
                3 -> Color.parseColor("#ffeb00")
                4 -> Color.parseColor("#00ff00")
                else -> Color.WHITE
            }
        }
    }
}
package com.ch2ps008.atomichabits.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps008.atomichabits.databinding.PredictItemBinding
import com.ch2ps008.atomichabits.db.Predict
import com.ch2ps008.atomichabits.db.PredictDao
import com.ch2ps008.atomichabits.util.formatHour
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PredictAdapter (private val predictDao: PredictDao) :
    RecyclerView.Adapter<PredictAdapter.ViewHolder>() {

    private var predict: List<Predict> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PredictItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, predictDao)
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

    class ViewHolder(private val binding: PredictItemBinding, private val predictDao: PredictDao) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var getPredict: Predict

        init {
            binding.btnDelete.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    predictDao.deletePredict(getPredict)
                }
            }
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
                0 -> Color.RED
                1 -> Color.parseColor("#FFA500")
                2 -> Color.parseColor("#00FF00")
                3 -> Color.GREEN
                else -> Color.WHITE
            }
        }
    }
}
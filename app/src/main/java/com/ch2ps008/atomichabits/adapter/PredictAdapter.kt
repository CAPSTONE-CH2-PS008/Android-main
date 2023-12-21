package com.ch2ps008.atomichabits.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps008.atomichabits.databinding.PredictItemBinding
import com.ch2ps008.atomichabits.db.Predict
import com.ch2ps008.atomichabits.db.PredictDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        lateinit var getPredict: Predict

        init {
            binding.btnDelete.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    predictDao.deletePredict(getPredict)
                }
            }
        }
        
        fun bind(habit: Predict) {
            getPredict = habit
            binding.apply {
                tvYourActivity.text = habit.activityName
                tvYourActivity.setTextColor(getColorForResult(habit.result))
                itemView.setOnClickListener {  }
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
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps008.atomichabits.databinding.HabitItemBinding
import com.ch2ps008.atomichabits.db.Habit

class HabitAdapter(private val onItemClick: (Habit) -> Unit) :
    RecyclerView.Adapter<HabitAdapter.ViewHolder>() {

    private var habits: List<Habit> = listOf()

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
        holder.itemView.setOnClickListener { onItemClick(habit) }
    }

    override fun getItemCount(): Int = habits.size

    fun submitList(newHabits: List<Habit>) {
        val diffResult = DiffUtil.calculateDiff(
            HabitDiffCallback(habits, newHabits)
        )
        habits = newHabits
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: HabitItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(habit: Habit) {
            binding.tvYourActivity.text = habit.activityName
            // Add other bindings as needed
        }
    }

    private class HabitDiffCallback(
        private val oldList: List<Habit>,
        private val newList: List<Habit>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}

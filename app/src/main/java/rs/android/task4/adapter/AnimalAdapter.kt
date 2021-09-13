package rs.android.task4.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import rs.android.task4.databinding.RecyclerViewItemBinding
//import rs.android.task4.db.dao.Animal
import rs.android.task4.repository.Animal

class AnimalAdapter(private var clickListener: OnAnimalItemClickListener) :
    ListAdapter<Animal, AnimalViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerViewItemBinding.inflate(layoutInflater, parent, false)
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<Animal>() {

            override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
                return oldItem.name == newItem.name &&
                        oldItem.age == newItem.age &&
                        oldItem.breed == newItem.breed
            }
        }
    }
}

interface OnAnimalItemClickListener {
    fun onItemClick(item: Animal)
}

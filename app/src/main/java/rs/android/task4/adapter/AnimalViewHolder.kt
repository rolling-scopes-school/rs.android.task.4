package rs.android.task4.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import androidx.recyclerview.widget.RecyclerView
import rs.android.task4.R
import rs.android.task4.databinding.RecyclerViewItemBinding
import rs.android.task4.repository.Animal

class AnimalViewHolder(private val binding: RecyclerViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var item: Animal? = null
        private set

    fun bind(animal: Animal, action: OnAnimalItemClickListener) {
        this.item = animal
        binding.name.text =
            getSpanableString(this.itemView.context.getString(R.string.item_rv_name), animal.name)
        binding.age.text =
            getSpanableString(
                this.itemView.context.getString(R.string.item_rv_age),
                animal.age.toString()
            )
        binding.breed.text =
            getSpanableString(this.itemView.context.getString(R.string.item_rv_breed), animal.breed)
        binding.animal.setOnClickListener { action.onItemClick(animal) }
    }

    private fun getSpanableString(word1: String, word2: String): SpannableString {
        val stringSpan = SpannableString("$word1 $word2")
        stringSpan.setSpan(
            ForegroundColorSpan(Color.BLUE),
            word1.length,
            word1.length + word2.length + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        stringSpan.setSpan(
            AbsoluteSizeSpan(
                this.itemView.context.resources.getInteger(R.integer.textSizeItems),
                true
            ),
            word1.length,
            word1.length + word2.length + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return stringSpan
    }
}
package com.omelchenkoaleks.storageroomorcursor.adapter

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.omelchenkoaleks.storageroomorcursor.databinding.AnimalItemBinding
import com.omelchenkoaleks.storageroomorcursor.model.Animal

class AnimalsHolder(
    binding: AnimalItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    private var animal: Animal? = null
    private val name: TextView = binding.tvNameItem
    private val age: TextView = binding.tvAgeItem
    private val breed: TextView = binding.tvBreedItem

    fun bind(animal: Animal) {
        this.animal = animal
        name.text = animal.name
        age.text = animal.age.toString()
        breed.text = animal.breed

        // TODO: will need to add listener
    }
}
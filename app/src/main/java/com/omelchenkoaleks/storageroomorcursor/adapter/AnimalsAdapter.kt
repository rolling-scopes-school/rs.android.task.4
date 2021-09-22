package com.omelchenkoaleks.storageroomorcursor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.omelchenkoaleks.storageroomorcursor.databinding.AnimalItemBinding
import com.omelchenkoaleks.storageroomorcursor.model.Animal

// TODO: will need to add param listener
class AnimalsAdapter : ListAdapter<Animal, AnimalsHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AnimalItemBinding.inflate(layoutInflater, parent, false)
        return AnimalsHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalsHolder, position: Int) {
        val animal = currentList[position]
        holder.bind(animal)
    }

    override fun getItemCount() = currentList.size

}
package com.omelchenkoaleks.storageroomorcursor.adapter

import androidx.recyclerview.widget.DiffUtil
import com.omelchenkoaleks.storageroomorcursor.model.Animal

object DiffCallback : DiffUtil.ItemCallback<Animal>() {
    override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
        return oldItem == newItem
    }
}
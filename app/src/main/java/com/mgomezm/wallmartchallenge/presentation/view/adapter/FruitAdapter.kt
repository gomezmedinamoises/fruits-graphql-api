package com.mgomezm.wallmartchallenge.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mgomezm.wallmartchallenge.FruitsListQuery
import com.mgomezm.wallmartchallenge.R
import com.mgomezm.wallmartchallenge.databinding.ItemFruitBinding

class FruitAdapter() : ListAdapter<FruitsListQuery.Fruit, FruitViewHolder>(FruitDiffUtil()) {

    var onItemClicked: ((FruitsListQuery.Fruit) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {
        val binding: ItemFruitBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_fruit,
            parent,
            false
        )
        return FruitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {
        holder.binding.fruit = getItem(position)
        // Open fruit detail screen
        val fruit = getItem(position)
        holder.binding.root.setOnClickListener {
            onItemClicked?.invoke(fruit)
        }
    }
}

class FruitViewHolder(val binding: ItemFruitBinding) : RecyclerView.ViewHolder(binding.root)

class FruitDiffUtil : DiffUtil.ItemCallback<FruitsListQuery.Fruit>() {
    override fun areItemsTheSame(
        oldItem: FruitsListQuery.Fruit,
        newItem: FruitsListQuery.Fruit
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: FruitsListQuery.Fruit,
        newItem: FruitsListQuery.Fruit
    ): Boolean {
        return oldItem == newItem
    }
}
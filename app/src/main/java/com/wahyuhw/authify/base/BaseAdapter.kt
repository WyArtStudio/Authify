package com.wahyuhw.authify.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB : ViewBinding, VH : BaseViewHolder<T>>(
    diffUtil: DiffUtil.ItemCallback<T>,
) : ListAdapter<T, VH>(
    diffUtil
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = createViewBinding(inflater, parent)
        return createViewHolder(binding)
    }
    abstract fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): VB

    abstract fun createViewHolder(binding: VB): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

}
package com.wahyuhw.authify.utils.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.wahyuhw.authify.domain.user.model.User

class DiffUtilUser : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}
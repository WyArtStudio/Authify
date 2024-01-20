package com.wahyuhw.authify.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wahyuhw.authify.R
import com.wahyuhw.authify.base.BaseAdapter
import com.wahyuhw.authify.base.BaseViewHolder
import com.wahyuhw.authify.databinding.ItemUserBinding
import com.wahyuhw.authify.domain.user.model.User
import com.wahyuhw.authify.utils.addOrUpdateItems
import com.wahyuhw.authify.utils.diffutil.DiffUtilUser
import com.wahyuhw.authify.utils.loadImageUrl

class UserAdapter(
    private val context: Context
) : BaseAdapter<User, ItemUserBinding, UserAdapter.UserVH>(DiffUtilUser()) {
    inner class UserVH(override val binding: ItemUserBinding) : BaseViewHolder<User>(binding) {

        override fun bind(data: User) {
            with(binding) {
				tvName.text = context.getString(
					R.string.label_first_last_name_format,
					data.firstName,
					data.lastName
				)
	            
	            tvEmail.text = data.email
	            
				imgUser.loadImageUrl(
					imgUrl = data.avatar,
					imgPlaceHolder = R.drawable.ic_account
				)
            }
        }
    }
	
	fun addOrUpdate(newList: List<User>) {
		val updatedList = addOrUpdateItems(
			oldList = this.currentList,
			newItems = newList
		) { data ->
			data.id
		}
		this.submitList(updatedList)
	}

    override fun createViewBinding(
	    inflater: LayoutInflater,
	    parent: ViewGroup
    ): ItemUserBinding {
        return ItemUserBinding.inflate(inflater, parent, false)
    }
	
	override fun createViewHolder(binding: ItemUserBinding): UserVH {
		return UserVH(binding)
	}
}
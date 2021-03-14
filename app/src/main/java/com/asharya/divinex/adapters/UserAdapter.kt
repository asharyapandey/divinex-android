package com.asharya.divinex.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.asharya.divinex.R
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.model.User
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(val context: Context) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var userList = emptyList<User>()

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civSearchUser: CircleImageView
        val tvSearchUsername: TextView
        init {
            civSearchUser= itemView.findViewById(R.id.civSearchUser)
            tvSearchUsername = itemView.findViewById(R.id.tvSearchUsername)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        holder.tvSearchUsername.text = user.username

        var postImagePath = ServiceBuilder.loadImagePath() + user.profilePicture
        postImagePath = postImagePath.replace("\\", "/")
        Glide.with(context).load(postImagePath).into(holder.civSearchUser)
    }

    override fun getItemCount() = userList.size

    fun addUserList(list: List<User>) {
        userList = list
        notifyDataSetChanged()
    }

    fun submitList(newUserList: List<User>) {
        val oldUserList = userList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            UserItemDiffCallback(
                oldUserList,
                newUserList
            )
        )
        userList = newUserList
        diffResult.dispatchUpdatesTo(this)
    }

    class UserItemDiffCallback(
        var oldUserList: List<User>,
        var newUserList: List<User>
    ): DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldUserList.size
        }

        override fun getNewListSize(): Int {
            return newUserList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldUserList[oldItemPosition]._id == newUserList[newItemPosition]._id)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldUserList[oldItemPosition].equals(newUserList[newItemPosition])
        }

    }
}

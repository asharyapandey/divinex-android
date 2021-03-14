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

class UserAdapter(val context: Context, val listener: OnItemClick) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var userList = emptyList<User>()

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civSearchUser: CircleImageView
        val tvSearchUsername: TextView
        init {
            civSearchUser= itemView.findViewById(R.id.civSearchUser)
            tvSearchUsername = itemView.findViewById(R.id.tvSearchUsername)

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                listener.onClick(userList[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        holder.tvSearchUsername.text = user.username

        var profileImagePath = ServiceBuilder.loadImagePath()
        if (user.profilePicture != null) {
            profileImagePath += user.profilePicture ?: ""
            profileImagePath = profileImagePath.replace("\\", "/")
        } else {
            profileImagePath += "images/profile_picture/male_img.png"
        }
        Glide.with(context).load(profileImagePath).into(holder.civSearchUser)
    }

    override fun getItemCount() = userList.size

    interface OnItemClick {
        fun onClick(user: User)
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

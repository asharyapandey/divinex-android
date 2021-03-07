package com.asharya.divinex.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asharya.divinex.R
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.entity.UserPost
import com.asharya.divinex.model.User
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

class UserAdapter(val context: Context) : RecyclerView.Adapter<UserAdapter.UserPostViewHolder>() {
    private var userList = emptyList<User>()

    inner class UserPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civSearchUser: CircleImageView
        val tvSearchUsername: TextView
        init {
            civSearchUser= itemView.findViewById(R.id.civSearchUser)
            tvSearchUsername = itemView.findViewById(R.id.tvSearchUsername)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return UserPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserPostViewHolder, position: Int) {
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
}

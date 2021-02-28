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
import com.asharya.divinex.model.Post
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class UserPostsAdapter(val context: Context) : RecyclerView.Adapter<UserPostsAdapter.UserPostViewHolder>() {
    private var postList = emptyList<UserPost>()

    inner class UserPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProfilePosts: ImageView
        init {
            ivProfilePosts = itemView.findViewById(R.id.ivProfilePosts)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_profile_item, parent, false)
        return UserPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserPostViewHolder, position: Int) {
        val post = postList[position]


        var postImagePath = ServiceBuilder.loadImagePath() + post.image
        val wat = "http:/10.0.2.2:5000/images/posts/POST-Wed Feb 17 2021-vader.jpg"
        postImagePath = postImagePath.replace("\\", "/")
        Glide.with(context).load(postImagePath).into(holder.ivProfilePosts)
    }

    override fun getItemCount() = postList.size

    fun addPostList(list: List<UserPost>) {
        postList = list
        notifyDataSetChanged()
    }
}

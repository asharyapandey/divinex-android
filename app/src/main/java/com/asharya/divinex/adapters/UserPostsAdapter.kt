package com.asharya.divinex.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.asharya.divinex.R
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.entity.Post
import com.bumptech.glide.Glide

class UserPostsAdapter(val context: Context) : RecyclerView.Adapter<UserPostsAdapter.UserPostViewHolder>() {
    private var postList = emptyList<Post>()

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
    fun submitList(newPostList: List<Post>) {
        val oldPostList= postList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            UserItemDiffCallback(
                oldPostList,
                newPostList
            )
        )
        postList= newPostList
        diffResult.dispatchUpdatesTo(this)
    }

    class UserItemDiffCallback(
        var oldPostList: List<Post>,
        var newPostList: List<Post>
    ): DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldPostList.size
        }

        override fun getNewListSize(): Int {
            return newPostList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldPostList[oldItemPosition]._id == newPostList[newItemPosition]._id)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldPostList[oldItemPosition] == newPostList[newItemPosition])
        }
    }

    override fun getItemCount() = postList.size
}

package com.asharya.divinex.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.asharya.divinex.R
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.entity.Post
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class NewsFeedAdapter(val context: Context) : RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder>() {
    private var postList = emptyList<Post>()

    inner class NewsFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCardProfile: CircleImageView
        val tvProfileName: TextView
        val ivPhoto: ImageView
        val tvProfileNameCaption: TextView

        val tvCaption : TextView
        val ibMore: ImageButton

        init {
            imgCardProfile = itemView.findViewById(R.id.imgCardProfile)
            tvProfileName = itemView.findViewById(R.id.tvProfileName)
            tvProfileNameCaption = itemView.findViewById(R.id.tvProfileNameCaption)
            ivPhoto = itemView.findViewById(R.id.ivPhoto)
            tvCaption = itemView.findViewById(R.id.tvCaption)
            ibMore = itemView.findViewById(R.id.ibMore)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return NewsFeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        val post = postList[position]

        holder.tvCaption.text = post.caption
        holder.tvProfileName.text = post.username ?: ""
        holder.tvProfileNameCaption.text = post.username ?: ""

        if (post.userID != ServiceBuilder.currentUser?._id) {
           holder.ibMore.visibility = View.INVISIBLE
        }

        var postImagePath = ServiceBuilder.loadImagePath() + post.image
        postImagePath = postImagePath.replace("\\", "/")
        Glide.with(context).load(postImagePath).into(holder.ivPhoto)

        var profileImagePath = ServiceBuilder.loadImagePath()
        if (post.profilePicture != null) {
            profileImagePath += post.profilePicture ?: ""
            profileImagePath = profileImagePath.replace("\\", "/")
        } else {
            profileImagePath += "images/profile_picture/male_img.png"
        }
        Glide.with(context).load(profileImagePath).into(holder.imgCardProfile)
    }

    override fun getItemCount() = postList.size

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
}

package com.asharya.divinex.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asharya.divinex.R
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.entity.FeedPost
import com.asharya.divinex.model.Post
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class NewsFeedAdapter(val context: Context) : RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder>() {
    private var postList = emptyList<FeedPost>()

    inner class NewsFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCardProfile: CircleImageView
        val tvProfileName: TextView
        val ivPhoto: ImageView
        val tvProfileNameCaption: TextView

        val tvCaption : TextView

        init {
            imgCardProfile = itemView.findViewById(R.id.imgCardProfile)
            tvProfileName = itemView.findViewById(R.id.tvProfileName)
            tvProfileNameCaption = itemView.findViewById(R.id.tvProfileNameCaption)
            ivPhoto = itemView.findViewById(R.id.ivPhoto)
            tvCaption = itemView.findViewById(R.id.tvCaption)
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

        var postImagePath = ServiceBuilder.loadImagePath() + post.image
        val wat = "http:/10.0.2.2:5000/images/posts/POST-Wed Feb 17 2021-vader.jpg"
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

    fun addPostList(list: List<FeedPost>) {
        postList = list
        notifyDataSetChanged()
    }
}

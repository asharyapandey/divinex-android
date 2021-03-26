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
import java.time.Duration
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

class NewsFeedAdapter(val context: Context, val listenerPost: PostClickListener) : RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder>() {
    private var postList = emptyList<Post>()

    inner class NewsFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCardProfile: CircleImageView
        val tvProfileName: TextView
        val ivPhoto: ImageView
        val tvProfileNameCaption: TextView

        val tvCaption : TextView
        val ibMore: ImageButton

        val tvViewComments: TextView
        val tvTime: TextView

        init {
            imgCardProfile = itemView.findViewById(R.id.imgCardProfile)
            tvProfileName = itemView.findViewById(R.id.tvProfileName)
            tvProfileNameCaption = itemView.findViewById(R.id.tvProfileNameCaption)
            ivPhoto = itemView.findViewById(R.id.ivPhoto)
            tvCaption = itemView.findViewById(R.id.tvCaption)
            ibMore = itemView.findViewById(R.id.ibMore)
            tvViewComments = itemView.findViewById(R.id.tvViewComments)
            tvTime = itemView.findViewById(R.id.tvTime)

            ibMore.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                listenerPost.onIbMoreClick(postList[position], it)
            }

            tvViewComments.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                    listenerPost.onViewCommentsClick(postList[position]._id)
            }
            tvProfileName.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                    listenerPost.onUsernameClick(postList[position].userID!!)
            }

            tvProfileNameCaption.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                    listenerPost.onUsernameClick(postList[position].userID!!)
            }
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
        val now = OffsetDateTime.now()
        val blah = post.createdAt?.until(now, ChronoUnit.HOURS)

        holder.tvTime.text = blah.toString() + " Hours Ago"
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

    interface PostClickListener {
        fun onIbMoreClick(post: Post, view: View)
        fun onViewCommentsClick(postID: String)
        fun onUsernameClick(userID: String)
    }
}

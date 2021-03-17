package com.asharya.divinex.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.asharya.divinex.R
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.entity.Comment
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class CommentAdapter(val context: Context, val listener: OnItemClick) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    private var commentList = emptyList<Comment>()

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civCommentProfile: CircleImageView
        val tvCommentUsername: TextView
        val tvComment: TextView
        val ibActions: ImageButton

        init {
            civCommentProfile = itemView.findViewById(R.id.civCommentProfile)
            tvCommentUsername = itemView.findViewById(R.id.tvCommentUsername)
            tvComment = itemView.findViewById(R.id.tvComment)
            ibActions = itemView.findViewById(R.id.ibActions)

            ibActions.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                    listener.onIbActionsClick(commentList[position], it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentList[position]

        holder.tvCommentUsername.text = comment.username
        holder.tvComment.text = comment.comment

        var profileImagePath = ServiceBuilder.loadImagePath()
        if (comment.profilePicture != null) {
            profileImagePath += comment.profilePicture ?: ""
            profileImagePath = profileImagePath.replace("\\", "/")
        } else {
            profileImagePath += "images/profile_picture/male_img.png"
        }
        Glide.with(context).load(profileImagePath).into(holder.civCommentProfile)
    }

    override fun getItemCount() = commentList.size

    interface OnItemClick {
        fun onIbActionsClick(comment: Comment, view: View)
    }

    fun submitList(newUserList: List<Comment>) {
        val oldUserList = commentList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            UserItemDiffCallback(
                oldUserList,
                newUserList
            )
        )
        commentList = newUserList
        diffResult.dispatchUpdatesTo(this)
    }

    class UserItemDiffCallback(
        var oldCommentList: List<Comment>,
        var newCommentList: List<Comment>
    ): DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldCommentList.size
        }

        override fun getNewListSize(): Int {
            return newCommentList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldCommentList[oldItemPosition]._id == newCommentList[newItemPosition]._id)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldCommentList[oldItemPosition] == newCommentList[newItemPosition])
        }
    }
}

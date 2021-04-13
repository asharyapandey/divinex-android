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
import com.asharya.divinex.entity.Notification
import com.asharya.divinex.model.User
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class NotificatonAdapter(val context: Context) : RecyclerView.Adapter<NotificatonAdapter.NotificationViewHolder>() {
    private var notificationList= emptyList<Notification>()

    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civNotificationImage: CircleImageView
        val tvNotificationUsername: TextView
        val tvNotificationAction: TextView
        init {
            civNotificationImage= itemView.findViewById(R.id.civNotificationImage)
            tvNotificationUsername= itemView.findViewById(R.id.tvNotificationUsername)
            tvNotificationAction= itemView.findViewById(R.id.tvNotificationAction)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification= notificationList[position]

        holder.tvNotificationUsername.text = notification.username
        if (notification.action == "Follow") {
            holder.tvNotificationAction.text = "Follows you."
        }

        var profileImagePath = ServiceBuilder.loadImagePath()
        if (notification.profilePicture != null) {
            profileImagePath += notification.profilePicture ?: ""
            profileImagePath = profileImagePath.replace("\\", "/")
        } else {
            profileImagePath += "images/profile_picture/male_img.png"
        }
        Glide.with(context).load(profileImagePath).into(holder.civNotificationImage)
    }

    override fun getItemCount() = notificationList.size

    interface OnItemClick {
        fun onClick(user: User)
    }

    fun submitList(newNotificationList: List<Notification>) {
        val oldNotificationList =notificationList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            UserItemDiffCallback(
                oldNotificationList,
                newNotificationList
            )
        )
        notificationList= newNotificationList
        diffResult.dispatchUpdatesTo(this)
    }

    class UserItemDiffCallback(
        var oldNotificationList: List<Notification>,
        var newNotificationList: List<Notification>
    ): DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldNotificationList.size
        }

        override fun getNewListSize(): Int {
            return newNotificationList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldNotificationList[oldItemPosition]._id == newNotificationList[newItemPosition]._id)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldNotificationList[oldItemPosition].equals(newNotificationList[newItemPosition])
        }
    }
}

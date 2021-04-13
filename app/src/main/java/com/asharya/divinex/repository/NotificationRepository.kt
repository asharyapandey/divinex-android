package com.asharya.divinex.repository

import android.util.Log
import com.asharya.divinex.api.ApiRequest
import com.asharya.divinex.api.PostAPI
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.api.UserAPI
import com.asharya.divinex.dao.CommentDAO
import com.asharya.divinex.dao.NotificationDAO
import com.asharya.divinex.entity.Comment
import com.asharya.divinex.entity.Notification
import com.asharya.divinex.response.AddCommentResponse
import java.lang.Exception

class NotificationRepository(private val notificationDAO: NotificationDAO) : ApiRequest() {
    private val userAPI = ServiceBuilder.buildService(UserAPI::class.java)

    // add comment
    suspend fun getNotifications(): List<Notification> {
        return try {
            refreshNotifications()
            notificationDAO.retrieveNotification()
        } catch (ex: Exception) {
            Log.e("Notification Repo", ex.toString())
            notificationDAO.retrieveNotification()
        }
    }

    private suspend fun refreshNotifications() {
        try {
            val response = apiRequest {
                userAPI.getNotifications(ServiceBuilder.token!!)
            }
            if (response.success == true) {
                for (notification in response.notifications!!) {
                    notificationDAO.insertNotification(
                        Notification(
                            _id = notification._id,
                            action = notification.action,
                            userID = notification.user?._id,
                            username = notification.user?.username,
                            profilePicture = notification.user?.profilePicture
                        )
                    )
                }
            }

        } catch (ex: Exception) {
            Log.e("CommentRepo", ex.toString())
        }
    }
}
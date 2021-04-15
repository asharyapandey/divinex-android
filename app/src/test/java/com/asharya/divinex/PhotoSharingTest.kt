package com.asharya.divinex

import android.content.Context
import android.os.Build.VERSION_CODES.O
import com.asharya.divinex.db.DivinexDB
import com.asharya.divinex.model.User
import com.asharya.divinex.repository.UserRepository
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.repository.CommentRepository
import com.asharya.divinex.repository.NotificationRepository
import com.asharya.divinex.repository.PostRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [O])
class PhotoSharingTest {
    private lateinit var userRepository: UserRepository
    private lateinit var postRepository: PostRepository
    private lateinit var commentRepository: CommentRepository
    private lateinit var notificationRepository: NotificationRepository
    private lateinit var context: Context


    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().context
        ServiceBuilder.token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwMmE0NGE3ZjY5NWYzMDY2Yzc1ZTVjMiIsInVzZXJuYW1lIjoiYXNoIiwiaWF0IjoxNjEzNTQ5MzUwfQ._oW0o75JQ_PcKY3Zev4gRxRJJtuyNvw2WwWpjbjIgIM"
        ServiceBuilder.currentUser =
            User(_id = "602cec1e1876a22ea84ac77b", "ash", "info@ash.com", "Male")
    }


    @Test
    fun testLogin() = runBlocking {
        val userDao = DivinexDB.getInstance(context).getUserDAO()
        userRepository = UserRepository(userDao)
        val response = userRepository.loginUser("ash", "wakandaforever")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun testGetUserbyId() = runBlocking {
        val userDao = DivinexDB.getInstance(context).getUserDAO()
        userRepository = UserRepository(userDao)
        val response = userRepository.getUserById("602cec1e1876a22ea84ac77b")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun testGetUser() = runBlocking {
        val userDao = DivinexDB.getInstance(context).getUserDAO()
        userRepository = UserRepository(userDao)
        val response = userRepository.getCurrentUser()
        val expectedResult = "ash"
        val actualResult = response.username
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun testGetSearchedUser() = runBlocking {
        val userDao = DivinexDB.getInstance(context).getUserDAO()
        userRepository = UserRepository(userDao)
        val users = userRepository.getSearchedUsers("a")
        val expectedResult = true
        val actualResult = users.isNotEmpty()
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun testGetNewsFeed() = runBlocking {
        val postDao = DivinexDB.getInstance(context).getPostDAO()
        postRepository = PostRepository(postDao)
        val posts = postRepository.getPostFeed()
        val expectedResult = true
        val actualResult = posts.isEmpty()
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun testGetUserPost() = runBlocking {
        val postDao = DivinexDB.getInstance(context).getPostDAO()
        postRepository = PostRepository(postDao)
        val posts = postRepository.getUserPosts("602cec1e1876a22ea84ac77b")
        val expectedResult = true
        val actualResult = posts.isNotEmpty()
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun testGetComments() = runBlocking {
        val commentDao = DivinexDB.getInstance(context).getCommentDAO()
        commentRepository = CommentRepository(commentDao)
        val comments = commentRepository.getComments("602cd0c5b4b3b231e0c4ba77")
        val expectedResult = true
        val actualResult = comments.isNotEmpty()
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun testGetNotifications() = runBlocking {
        val notificationDao= DivinexDB.getInstance(context).getNotificationDAO()
        notificationRepository = NotificationRepository(notificationDao)
        val notifications = notificationRepository.getNotifications()
        val expectedResult = true
        val actualResult = notifications.isEmpty()
        Assert.assertEquals(expectedResult, actualResult)
    }
}
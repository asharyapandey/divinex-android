package com.asharya.divinex

import com.asharya.divinex.model.User
import com.asharya.divinex.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class PhotoSharingTest {
    private lateinit var userRepository: UserRepository

    @Test
    fun testLogin() = runBlocking {
        userRepository = UserRepository()
        val response = userRepository.loginUser("ash","wakandaforever")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }


}
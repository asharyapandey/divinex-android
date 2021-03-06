package com.asharya.divinex.ui.fragments.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asharya.divinex.repository.PostRepository
import com.asharya.divinex.repository.UserRepository

class ViewProfileViewModelFactory(private val userRepository: UserRepository, private val postRepository: PostRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return ViewProfileViewModel(userRepository, postRepository) as T
    }
}
package com.asharya.divinex.ui.fragments.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asharya.divinex.repository.PostRepository
import com.asharya.divinex.repository.UserRepository

class EditProfileViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return EditProfileViewModel(repository) as T
    }
}
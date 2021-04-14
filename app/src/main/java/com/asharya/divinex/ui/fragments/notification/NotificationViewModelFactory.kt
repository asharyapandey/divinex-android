package com.asharya.divinex.ui.fragments.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asharya.divinex.repository.NotificationRepository
import com.asharya.divinex.repository.PostRepository

class NotificationViewModelFactory(private val repository: NotificationRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return NotificationViewModel(repository) as T
    }
}
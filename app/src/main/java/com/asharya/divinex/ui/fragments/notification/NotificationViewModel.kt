package com.asharya.divinex.ui.fragments.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.entity.Notification
import com.asharya.divinex.entity.Post
import com.asharya.divinex.repository.NotificationRepository
import com.asharya.divinex.repository.PostRepository
import kotlinx.coroutines.launch

class NotificationViewModel(private val repository: NotificationRepository): ViewModel() {

    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications : LiveData<List<Notification>>
    get() = _notifications

    fun getPosts() {
        viewModelScope.launch {
            _notifications.value = repository.getNotifications()
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
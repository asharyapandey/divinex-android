package com.asharya.divinex.ui.fragments.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.asharya.divinex.R
import com.asharya.divinex.adapters.NotificationAdapter
import com.asharya.divinex.db.DivinexDB
import com.asharya.divinex.repository.NotificationRepository

class NotificationFragment : Fragment(R.layout.fragment_notification) {
    private lateinit var rvNotification: RecyclerView
    private lateinit var viewModel: NotificationViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvNotification = view.findViewById(R.id.rvNotifcation)
        val adapter = NotificationAdapter(requireContext())
        rvNotification.adapter = adapter

        val notificationDAO = DivinexDB.getInstance(requireContext()).getNotificationDAO()
        val repository = NotificationRepository(notificationDAO)
        viewModel = ViewModelProvider(this, NotificationViewModelFactory(repository)).get(NotificationViewModel::class.java)

        viewModel.getPosts()

        viewModel.notifications.observe(viewLifecycleOwner, Observer { notifications ->
            adapter.submitList(notifications)
        })
    }
}
package com.asharya.divinex.ui.fragments.notification

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.asharya.divinex.R
import com.asharya.divinex.adapters.NotificationAdapter
import com.asharya.divinex.db.DivinexDB
import com.asharya.divinex.repository.NotificationRepository
import com.asharya.divinex.utils.NotificationChannels

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
            for (notification in notifications) {
                val notiString = "${notification.username} started following you"
                showLowPriorityNotification(notiString)
            }
        })
    }


    private fun showLowPriorityNotification(notificationString: String) {
        val notificationManager = NotificationManagerCompat.from(requireContext())

        val notificationChannels = NotificationChannels(requireContext())
        notificationChannels.createNotificationChannels()

        val notification = NotificationCompat.Builder(requireContext(), notificationChannels.CHANNEL_2)
            .setSmallIcon(R.drawable.ic_noti)
            .setContentTitle("DivineX")
            .setContentText(notificationString)
            .setColor(Color.BLACK)
            .build()
        notificationManager.notify(1, notification)
    }
}
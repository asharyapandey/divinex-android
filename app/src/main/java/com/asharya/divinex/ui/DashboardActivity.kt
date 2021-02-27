package com.asharya.divinex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.asharya.divinex.R
import com.asharya.divinex.ui.fragments.addphoto.AddPhotoFragment
import com.asharya.divinex.ui.fragments.home.HomeFragment
import com.asharya.divinex.ui.fragments.notification.NotificationFragment
import com.asharya.divinex.ui.fragments.profile.ProfileFragment
import com.asharya.divinex.ui.fragments.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {
    private lateinit var bnvDashboard : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        bnvDashboard = findViewById(R.id.bnvDashboard)

        val homeFragment = HomeFragment()
        val notificationFragment= NotificationFragment()
        val addPhotoFragment = AddPhotoFragment()
        val searchFragment = SearchFragment()
        val profileFragment = ProfileFragment()

        setFragment(homeFragment)

        bnvDashboard.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.miHome -> setFragment(homeFragment)
                R.id.miProfile -> setFragment(profileFragment)
                R.id.miNotifiation -> setFragment(notificationFragment)
                R.id.miSearch -> setFragment(searchFragment)
                R.id.miAddPhoto -> setFragment(addPhotoFragment)
            }
            true
        }


    }

    private fun setFragment(fragment : Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            addToBackStack(null)
            commit()
        }
}
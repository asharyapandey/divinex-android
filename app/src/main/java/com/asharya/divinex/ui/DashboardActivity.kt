package com.asharya.divinex.ui

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.asharya.divinex.R
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.ui.fragments.addpost.AddPostFragment
import com.asharya.divinex.ui.fragments.home.HomeFragment
import com.asharya.divinex.ui.fragments.notification.NotificationFragment
import com.asharya.divinex.ui.fragments.profile.ProfileFragment
import com.asharya.divinex.ui.fragments.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {
    private lateinit var bnvDashboard: BottomNavigationView
    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        bnvDashboard = findViewById(R.id.bnvDashboard)

        val homeFragment = HomeFragment()
        val notificationFragment = NotificationFragment()
        val addPhotoFragment = AddPostFragment()
        val searchFragment = SearchFragment()
        val profileFragment = ProfileFragment()

        checkRunTimePermission()
        setFragment(homeFragment)
        Toast.makeText(this, "${ServiceBuilder.token}", Toast.LENGTH_SHORT).show()

        bnvDashboard.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miHome -> setFragment(homeFragment)
                R.id.miProfile -> setFragment(profileFragment)
                R.id.miNotifiation -> setFragment(notificationFragment)
                R.id.miSearch -> setFragment(searchFragment)
                R.id.miAddPhoto -> setFragment(addPhotoFragment)
            }
            true
        }


    }

    private fun setFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            addToBackStack(null)
            commit()
        }

    private fun checkRunTimePermission() {
        if (!hasPermission()) {
            requestPermission()
        }
    }

    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
                break
            }
        }
        return hasPermission
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this@DashboardActivity, permissions, 1)
    }
}
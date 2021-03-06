package com.asharya.divinexwear

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.TextView
import com.asharya.divinex.api.ServiceBuilder
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

class DashboardActivity : WearableActivity() {
    private lateinit var tvUsername: TextView
    private lateinit var civProfile : CircleImageView
    private lateinit var tvEmail : TextView
    private lateinit var tvFollowing: TextView
    private lateinit var tvFollowers: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Enables Always-on
        setAmbientEnabled()

        tvUsername = findViewById(R.id.tvUsername)
        tvEmail= findViewById(R.id.tvEmail)
        tvFollowers= findViewById(R.id.tvFollowers)
        tvFollowing= findViewById(R.id.tvFollowinge)
        civProfile = findViewById(R.id.civProfile)
        tvUsername.text = "Hello, " + ServiceBuilder.currentUser?.username
        tvEmail.text = ServiceBuilder.currentUser?.email
        tvFollowing.text = ServiceBuilder.currentUser?.following?.size.toString() + " Following"
        tvFollowers.text = ServiceBuilder.currentUser?.followers?.size.toString() + " Followers"


        var profImagePath = ServiceBuilder.loadImagePath() + ServiceBuilder.currentUser?.profilePicture
        profImagePath = profImagePath.replace("\\", "/")
        Glide.with(this).load(profImagePath).into(civProfile)
    }
}
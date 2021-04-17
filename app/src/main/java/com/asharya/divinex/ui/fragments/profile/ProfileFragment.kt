package com.asharya.divinex.ui.fragments.profile

import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asharya.divinex.R
import com.asharya.divinex.adapters.UserPostsAdapter
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.db.DivinexDB
import com.asharya.divinex.entity.Post
import com.asharya.divinex.entity.User
import com.asharya.divinex.repository.PostRepository
import com.asharya.divinex.repository.UserRepository
import com.asharya.divinex.ui.login.LoginActivity
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment(), UserPostsAdapter.UserPostClickListener, SensorEventListener {
    private lateinit var civProfile: CircleImageView
    private lateinit var tvUsername: TextView
    private lateinit var viewModel: ProfileViewModel
    private lateinit var btnLoadMaps: Button
    private lateinit var btnEditProfile: Button
    private lateinit var rvUserPosts: RecyclerView
    private lateinit var tvFollowers: TextView
    private lateinit var tvFollowing: TextView
    private lateinit var tvPostNumber: TextView
    private lateinit var tvUsernameHeading: TextView
    private var theUser: User? = null
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    private var sensorCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        sensorManager = requireContext().getSystemService(SENSOR_SERVICE) as SensorManager

        civProfile = view.findViewById(R.id.civProfile)
        tvUsername = view.findViewById(R.id.tvUsername)
        btnLoadMaps = view.findViewById(R.id.btnLoadMaps)
        btnEditProfile = view.findViewById(R.id.btnEditProfile)
        rvUserPosts = view.findViewById(R.id.rvUserPosts)
        tvFollowers = view.findViewById(R.id.tvFollowers)
        tvFollowing = view.findViewById(R.id.tvFollowing)
        tvUsernameHeading = view.findViewById(R.id.tvUsernameHeading)
        tvPostNumber = view.findViewById(R.id.tvPostNumber)

        // for RV
        val adapter = context?.let { UserPostsAdapter(it, this) }
        rvUserPosts.adapter = adapter
        rvUserPosts.layoutManager = GridLayoutManager(context, 3)

        val userDao = DivinexDB.getInstance(requireContext()).getUserDAO()
        val repository = UserRepository(userDao)
        val postDao = context?.let { DivinexDB.getInstance(it).getPostDAO() }
        val postRepository = postDao?.let { PostRepository(it) }
        val sharedPref = requireContext().getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
        viewModel =
            ViewModelProvider(this, ProfileViewModelFactory(repository, postRepository!!, sharedPref)).get(
                ProfileViewModel::class.java
            )

        viewModel.getCurrentUser()

        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            tvUsername.text = user.username
            tvUsernameHeading.text = user.username
            if (user.profilePicture != null) {
                var profileImagePath = ServiceBuilder.loadImagePath() + user.profilePicture
                profileImagePath = profileImagePath.replace("\\", "/")
                Glide.with(requireContext()).load(profileImagePath).into(civProfile)
            }
            tvFollowers.text = user.followers.toString()
            tvFollowing.text = user.following.toString()
            theUser = user
            user._id?.let { viewModel.getCurrentUserPosts(it) }
        })

        viewModel.posts.observe(viewLifecycleOwner, Observer { posts ->
            adapter?.submitList(posts)
            tvPostNumber.text = posts.size.toString()
        })

        viewModel.isLoggedOut.observe(viewLifecycleOwner, Observer { isLoggedOut ->
            if (isLoggedOut) {
                startActivity(Intent(context, LoginActivity::class.java))
            } else {
                Toast.makeText(context, "Could not log out", Toast.LENGTH_SHORT).show()
            }
        })


        btnLoadMaps.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToMapsFragment()
            findNavController().navigate(action)
        }

        btnEditProfile.setOnClickListener {
            val action = theUser?.let { it1 ->
                ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(
                    it1
                )
            }
            if (action != null) {
                findNavController().navigate(action)
            }
        }

        tvUsernameHeading.setOnClickListener {
            val popupMenu = PopupMenu(context, it)
            popupMenu.menuInflater.inflate(R.menu.logout, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menuLogout -> logout()
                }
                true
            }
            popupMenu.show()
        }


        // for sensor

        if (checkSensor()) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        return view
    }

    override fun itemClicked(post: Post, position: Int) {
        val action = ProfileFragmentDirections.actionProfileFragmentToUserPostsFragment(
            post.userID!!,
            position
        )
        findNavController().navigate(action)
    }

    private fun checkSensor() = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null

    private fun logout() {
        viewModel.logout()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val value = event!!.values[0]

        if (value < 4) {
            sensorCounter++
        }
        if (sensorCounter == 2) {
            sensorCounter = 0
            logout()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
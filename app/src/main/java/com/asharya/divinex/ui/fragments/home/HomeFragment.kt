package com.asharya.divinex.ui.fragments.home

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.asharya.divinex.R
import com.asharya.divinex.adapters.NewsFeedAdapter
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.db.DivinexDB
import com.asharya.divinex.entity.Post
import com.asharya.divinex.repository.PostRepository
import java.lang.Exception
import java.lang.Math.sqrt
import kotlin.math.sqrt

class HomeFragment : Fragment(), NewsFeedAdapter.PostClickListener, SensorEventListener {
    private lateinit var rvFeedPosts: RecyclerView
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: NewsFeedAdapter
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    private var gyroSensor: Sensor? = null
    private val SHAKE_THRESHOLD_GRAVITY = 2.7f
    private val SHAKE_SLOP_TIME_MS = 500
    private val SHAKE_COUNT_RESET_TIME_MS = 3000
    private var mShakeTimestamp: Long = 0
    private var mShakeCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager


        val postDao = context?.let { DivinexDB.getInstance(it).getPostDAO() }
        val repository = postDao?.let { PostRepository(it) }
        viewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(repository!!)
        ).get(HomeViewModel::class.java)
        rvFeedPosts = view.findViewById(R.id.rvFeedPosts)

        adapter = context?.let { NewsFeedAdapter(it, this) }!!
        rvFeedPosts.adapter = adapter
        viewModel.getPosts()

        viewModel.posts.observe(viewLifecycleOwner, Observer { posts ->
            adapter.submitList(posts)
        })

        // for sensor

        if (checkSensor()) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            gyroSensor= sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            sensorManager.registerListener(gyroListener, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        return view
    }

    override fun onIbMoreClick(post: Post, view: View) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.update_delete, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuDelete -> delete()
                R.id.menuUpdate -> update()
            }
            true
        }
        popupMenu.show()
    }

    private val gyroListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            val value = event!!.values[2]
            if (value > 0.5f) { // anti
                rvFeedPosts.smoothScrollToPosition(0)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

    }
    override fun onViewCommentsClick(postID: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToCommentFragment(postID)
        findNavController().navigate(action)
    }

    override fun onUsernameClick(userID: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToViewProfileFragment(userID)
        findNavController().navigate(action)
    }

    private fun update() {
        TODO("Not yet implemented")
    }

    private fun delete() {
        TODO("Not yet implemented")
    }

    private fun checkSensor() =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null && sensorManager.getDefaultSensor(
            Sensor.TYPE_GYROSCOPE
        ) != null

    private fun refreshPost() {
        viewModel.getPosts()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        try {
            val x = event!!.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val gX = x / SensorManager.GRAVITY_EARTH
            val gY = y / SensorManager.GRAVITY_EARTH
            val gZ = z / SensorManager.GRAVITY_EARTH
            // gForce will be close to 1 when there is no movement.
            val gForce: Float = sqrt(gX * gX + gY * gY + gZ * gZ)
            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                val now = System.currentTimeMillis()
                // ignore shake events too close to each other (500ms)
                if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return
                }
                // reset the shake count after 3 seconds of no shakes
                if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    mShakeCount = 0
                }
                mShakeTimestamp = now
                mShakeCount++
                val action = HomeFragmentDirections.actionHomeFragmentToAddPostFragment()
                findNavController().navigate(action)
            }
        } catch (ex: Exception) {
            Toast.makeText(context, "Can't Handle Shake", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}
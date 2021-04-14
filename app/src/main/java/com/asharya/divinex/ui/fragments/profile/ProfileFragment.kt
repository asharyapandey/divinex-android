package com.asharya.divinex.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
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
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment(), UserPostsAdapter.UserPostClickListener {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

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
        viewModel =
            ViewModelProvider(this, ProfileViewModelFactory(repository, postRepository!!)).get(
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
            loadPopUpMenu()
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

    private fun loadPopUpMenu() {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.logout, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuLogout -> logout()
            }
            true
        }
        popupMenu.show()
    }

    private fun logout() {
    }
}
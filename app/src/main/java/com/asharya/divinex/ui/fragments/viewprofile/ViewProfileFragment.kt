package com.asharya.divinex.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asharya.divinex.R
import com.asharya.divinex.adapters.UserPostsAdapter
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.db.DivinexDB
import com.asharya.divinex.entity.Post
import com.asharya.divinex.repository.PostRepository
import com.asharya.divinex.repository.UserRepository
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class ViewProfileFragment : Fragment(), UserPostsAdapter.UserPostClickListener {
    private lateinit var civProfile: CircleImageView
    private lateinit var tvUsername: TextView
    private lateinit var viewModelView: ViewProfileViewModel
    private lateinit var rvUserPosts: RecyclerView
    private lateinit var tvFollowers: TextView
    private lateinit var tvFollowing: TextView
    private lateinit var tvPostNumber: TextView
    private lateinit var btnFollowUnfollow: Button

    private val args by navArgs<ViewProfileFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_profile, container, false)

        civProfile = view.findViewById(R.id.civProfile)
        tvUsername = view.findViewById(R.id.tvUsername)
        rvUserPosts = view.findViewById(R.id.rvUserPosts)
        tvFollowers = view.findViewById(R.id.tvFollowers)
        tvFollowing = view.findViewById(R.id.tvFollowing)
        tvPostNumber = view.findViewById(R.id.tvPostNumber)
        btnFollowUnfollow = view.findViewById(R.id.btnFollowUnfollow)

        // for showing follow or unfollow
        val currentUserFollowings = ServiceBuilder.currentUser?.following

//        for (user in currentUserFollowings!!) {
//            if (user.== args.userID)
//                btnFollowUnfollow.text = "Unfollow"
//        }

        // for RV
        val adapter = context?.let { UserPostsAdapter(it, this) }
        rvUserPosts.adapter = adapter
        rvUserPosts.layoutManager = GridLayoutManager(context, 3)

        val repository = UserRepository()
        val postDao = context?.let { DivinexDB.getInstance(it).getPostDAO() }
        val postRepository = postDao?.let { PostRepository(it) }
        viewModelView =
            ViewModelProvider(this, ViewProfileViewModelFactory(repository, postRepository!!)).get(
                ViewProfileViewModel::class.java
            )
        viewModelView.userID = args.userID
        viewModelView.getCurrentUser(args.userID)
        viewModelView.getCurrentUserPosts(args.userID)

        viewModelView.user.observe(viewLifecycleOwner, Observer { user ->
            tvUsername.text = user.username
            if (user.profilePicture != null) {
                var profileImagePath = ServiceBuilder.loadImagePath() + user.profilePicture
                profileImagePath = profileImagePath.replace("\\", "/")
                Glide.with(requireContext()).load(profileImagePath).into(civProfile)
            }
            tvFollowers.text = user.followers?.size.toString()
            tvFollowing.text = user.following?.size.toString()

            if (currentUserFollowings!!.contains(user)) {
                btnFollowUnfollow.text = "Unfollow"
            }
        })

        viewModelView.posts.observe(viewLifecycleOwner, Observer { posts ->
            adapter?.submitList(posts)
            tvPostNumber.text = posts.size.toString()
        })
        viewModelView.followed.observe(viewLifecycleOwner, Observer { isFollowed ->
            if (isFollowed)
                btnFollowUnfollow.text = "Unfollow"
            viewModelView.getCurrentUser(args.userID)

        })

        viewModelView.unfollowed.observe(viewLifecycleOwner, Observer { isUnFollowed ->
            if (isUnFollowed)
                btnFollowUnfollow.text = "Follow"
            viewModelView.getCurrentUser(args.userID)
        })
        btnFollowUnfollow.setOnClickListener {
            if (btnFollowUnfollow.text.toString() == "Follow") {
                viewModelView.followUser(args.userID)
            } else {
                viewModelView.unFollowUser(args.userID)
                Toast.makeText(context, "Followxa", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    override fun onStop() {
        viewModelView.deletePosts(args.userID)
        super.onStop()
    }

    override fun itemClicked(post: Post, position: Int) {
        val action = ViewProfileFragmentDirections.actionViewProfileFragmentToUserPostsFragment(
            post.userID!!,
            position
        )
        findNavController().navigate(action)
    }
}
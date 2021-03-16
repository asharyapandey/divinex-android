package com.asharya.divinex.ui.fragments.userposts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.asharya.divinex.R
import com.asharya.divinex.adapters.NewsFeedAdapter
import com.asharya.divinex.db.DivinexDB
import com.asharya.divinex.entity.Post
import com.asharya.divinex.repository.PostRepository

class UserPostsFragment : Fragment(), NewsFeedAdapter.PostClickListener {
    private lateinit var rvFeedPosts: RecyclerView
    private lateinit var viewModel: UserPostsViewModel
    private lateinit var adapter: NewsFeedAdapter

    private val args by navArgs<UserPostsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_posts, container, false)
        val postDao = context?.let { DivinexDB.getInstance(it).getPostDAO() }
        val repository = postDao?.let { PostRepository(it) }
        viewModel = ViewModelProvider(this, UserPostsViewModelFactory(repository!!)).get(UserPostsViewModel::class.java)
        rvFeedPosts = view.findViewById(R.id.rvFeedPosts)


        refreshPost()

        adapter = context?.let { NewsFeedAdapter(it, this) }!!
        rvFeedPosts.adapter = adapter

        viewModel.posts.observe(viewLifecycleOwner, Observer { posts ->
            adapter.submitList(posts)
            rvFeedPosts.scrollToPosition(args.position)
        })
        return view
    }

    override fun onIbMoreClick(post: Post, view: View) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.update_delete, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuDelete-> delete(post)
                R.id.menuUpdate-> update(post)
            }
            true
        }
        popupMenu.show()
    }

    private fun update(post: Post) {
        val action = UserPostsFragmentDirections.actionUserPostsFragmentToEditPostFragment(post)
        findNavController().navigate(action)
    }

    private fun delete(post: Post) {

        val builder = AlertDialog.Builder(requireContext())

        val alertDialog = builder.apply {
            setTitle("Delete this Post?")
            setMessage("This post will be deleted forever and can't be restored.")
            setIcon(R.drawable.ic_alert)

            setPositiveButton("Yes") {_, _ ->
                viewModel.deletePost(post)
                refreshPost()
            }

            setNegativeButton("No") {_, _ ->

            }

                .create()
        }

        alertDialog.show()



    }

    private fun refreshPost() {
        viewModel.getPosts(args.userID)
    }
}
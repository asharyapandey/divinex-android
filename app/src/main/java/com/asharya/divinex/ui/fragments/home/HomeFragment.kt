package com.asharya.divinex.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.asharya.divinex.R
import com.asharya.divinex.adapters.NewsFeedAdapter
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.db.DivinexDB
import com.asharya.divinex.repository.PostRepository

class HomeFragment : Fragment() {
    private lateinit var rvFeedPosts: RecyclerView
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: NewsFeedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val postDao = context?.let { DivinexDB.getInstance(it).getPostDAO() }
        val repository = postDao?.let { PostRepository(it) }
        viewModel = ViewModelProvider(this, HomeViewModelFactory(repository!!)).get(HomeViewModel::class.java)
        rvFeedPosts = view.findViewById(R.id.rvFeedPosts)

        adapter = context?.let { NewsFeedAdapter(it) }!!
        rvFeedPosts.adapter = adapter
        viewModel.getPosts()

        viewModel.posts.observe(viewLifecycleOwner, Observer { posts ->
            adapter.addPostList(posts)
        })
        return view
    }
}
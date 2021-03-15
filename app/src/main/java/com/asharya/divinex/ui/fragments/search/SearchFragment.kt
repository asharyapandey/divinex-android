package com.asharya.divinex.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.asharya.divinex.R
import com.asharya.divinex.adapters.UserAdapter
import com.asharya.divinex.model.User
import com.asharya.divinex.repository.UserRepository

class SearchFragment : Fragment(R.layout.fragment_search), UserAdapter.OnItemClick {
    private lateinit var etSearch: EditText
    private lateinit var viewModel: SearchViewModel
    private lateinit var rvSearch: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_search, container, false)
        etSearch = view.findViewById(R.id.etSearch)
        rvSearch = view.findViewById(R.id.rvSearch)
        val adapter = context?.let { UserAdapter(it, this) }
        rvSearch.adapter = adapter
        val repository = UserRepository()
        viewModel = ViewModelProvider(this, SearchViewModelFactory(repository)).get(SearchViewModel::class.java)

        viewModel.users.observe(viewLifecycleOwner, Observer { users ->
            adapter?.submitList(users)
        })

        etSearch.addTextChangedListener { text ->
            viewModel.getUsers(text.toString())
        }



        return view

    }

    override fun onClick(user: User) {
        val action = SearchFragmentDirections.actionSearchFragmentToViewProfileFragment(user._id!!)
        findNavController().navigate(action)
    }
}
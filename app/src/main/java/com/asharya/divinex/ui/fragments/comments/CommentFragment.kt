package com.asharya.divinex.ui.fragments.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.asharya.divinex.R
import com.asharya.divinex.db.DivinexDB
import com.asharya.divinex.repository.CommentRepository

class CommentFragment : Fragment() {
    private lateinit var viewModel: CommentViewModel
    private lateinit var etComment: EditText
    private lateinit var btnComment: Button

    private val args by navArgs<CommentFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comment, container, false)

        etComment = view.findViewById(R.id.etComment)
        btnComment = view.findViewById(R.id.btnComment)

        val commentDao = DivinexDB.getInstance(requireContext()).getCommentDAO()
        val repository = CommentRepository(commentDao)
        viewModel = ViewModelProvider(this, CommentViewModelFactory(repository)).get(CommentViewModel::class.java)

        refreshComments()

        viewModel.comments.observe(viewLifecycleOwner, Observer { comments ->

        })



        return view
    }

    fun onIbMoreClick(view: View) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.update_delete, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuDelete-> delete()
                R.id.menuUpdate-> update()
            }
            true
        }
        popupMenu.show()
    }

    private fun update() {
        TODO("Not yet implemented")
    }

    private fun delete() {
        TODO("Not yet implemented")
    }

    private fun refreshComments() {
        viewModel.getPosts(args.postID)
    }
}
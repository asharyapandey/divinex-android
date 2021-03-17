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
import androidx.recyclerview.widget.RecyclerView
import com.asharya.divinex.R
import com.asharya.divinex.adapters.CommentAdapter
import com.asharya.divinex.db.DivinexDB
import com.asharya.divinex.entity.Comment
import com.asharya.divinex.repository.CommentRepository

class CommentFragment : Fragment(), CommentAdapter.OnCommentClick {
    private lateinit var viewModel: CommentViewModel
    private lateinit var etComment: EditText
    private lateinit var btnComment: Button
    private lateinit var rvComment: RecyclerView

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
        rvComment = view.findViewById(R.id.rvComments)

        val commentDao = DivinexDB.getInstance(requireContext()).getCommentDAO()
        val repository = CommentRepository(commentDao)
        viewModel = ViewModelProvider(this, CommentViewModelFactory(repository)).get(CommentViewModel::class.java)

        refreshComments()

        val adapter = CommentAdapter(requireContext(), this)
        rvComment.adapter = adapter

        viewModel.comments.observe(viewLifecycleOwner, Observer { comments ->
            adapter.submitList(comments)
        })

        return view
    }

    override fun onIbActionsClick(comment: Comment, view: View) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.update_delete, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuDelete-> delete(comment)
                R.id.menuUpdate-> update(comment)
            }
            true
        }
        popupMenu.show()
    }

    private fun update(comment: Comment) {
        TODO("Not yet implemented")
    }

    private fun delete(comment: Comment) {
        TODO("Not yet implemented")
    }

    private fun refreshComments() {
        viewModel.getPosts(args.postID)
    }
}
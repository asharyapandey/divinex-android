package com.asharya.divinex.ui.fragments.comments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

    var currentComment: Comment = Comment("")

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

        viewModel.commentAdded.observe(viewLifecycleOwner, Observer { commentAdded ->
            if (commentAdded) {
                reset()
                Toast.makeText(context, "Comment was Added", Toast.LENGTH_SHORT).show()
            }
        })
        
        viewModel.commentUpdated.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        })


        btnComment.setOnClickListener {
            if (TextUtils.isEmpty(etComment.text)) {
                etComment.error = "Please Add a Comment"
                etComment.requestFocus()
                return@setOnClickListener
            }
            val comment = Comment(_id = "", comment = etComment.text.toString())

            if (btnComment.text.toString() == "comment") {
                addComment(comment)
            } else {
                updateComment(comment)
            }
        }

        return view
    }

    private fun updateComment(comment: Comment) {
        if (currentComment._id != "") {
            viewModel.updateComment(currentComment._id, comment)
            refreshComments()
        } else {
            Toast.makeText(context, "Select a Comment", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addComment(comment: Comment) {
        viewModel.addComment(args.postID, comment)
        refreshComments()
    }

    private fun reset() {
        etComment.setText("")
        btnComment.text = "comment"
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
        etComment.setText(comment.comment)
        btnComment.text = "Update Comment"
        currentComment = comment
    }

    private fun delete(comment: Comment) {
        val builder = AlertDialog.Builder(requireContext())

        val alertDialog = builder.apply {
            setTitle("Delete this Comment?")
            setMessage("This Comment will be deleted forever and can't be restored.")
            setIcon(R.drawable.ic_alert)

            setPositiveButton("Yes") { _, _ ->
                viewModel.deleteComment(comment)
                refreshComments()
                Toast.makeText(context, "Comment Deleted", Toast.LENGTH_SHORT).show()
            }

            setNegativeButton("No") { _, _ ->

            }
                .create()
        }
        alertDialog.show()
    }

    private fun refreshComments() {
        viewModel.getPosts(args.postID)
    }
}
package com.asharya.divinex.ui.fragments.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.asharya.divinex.R
import com.asharya.divinex.entity.Post

class CommentFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
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
    }
}
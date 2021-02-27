package com.asharya.divinex.ui.fragments.addpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.asharya.divinex.R
class AddPostFragment : Fragment() {
    private lateinit var viewModel: AddPostViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_post, container, false)

        val ivPostImage: ImageView = view.findViewById(R.id.ivPostImage)
        val etPostCaption: EditText = view.findViewById(R.id.etPostCaption)
        val btnUploadPost : Button = view.findViewById(R.id.btnUploadPost)

        ivPostImage.setOnClickListener {

        }
        return view
    }

}
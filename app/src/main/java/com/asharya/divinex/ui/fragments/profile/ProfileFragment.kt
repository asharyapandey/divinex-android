package com.asharya.divinex.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.asharya.divinex.R
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.repository.UserRepository
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {
    private lateinit var civProfile: CircleImageView
    private lateinit var tvUsername : TextView
    private lateinit var viewModel: ProfileViewModel
    private lateinit var btnLoadMaps: Button

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

        val repository = UserRepository()
        viewModel = ViewModelProvider(this, ProfileViewModelFactory(repository)).get(ProfileViewModel::class.java)

        viewModel.getCurrentUser()

        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            tvUsername.text = user.username
            if (user.profilePicture != null) {
                var profileImagePath = ServiceBuilder.loadImagePath() + user.profilePicture
                profileImagePath = profileImagePath.replace("\\", "/")
                Glide.with(requireContext()).load(profileImagePath).into(civProfile)
            }
        })


        btnLoadMaps.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToMapsFragment()
            findNavController().navigate(action)
        }
        return view
    }
}
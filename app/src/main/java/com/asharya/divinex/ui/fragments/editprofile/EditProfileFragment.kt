package com.asharya.divinex.ui.fragments.editprofile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.asharya.divinex.R
import com.asharya.divinex.api.ServiceBuilder
import com.asharya.divinex.db.DivinexDB
import com.asharya.divinex.repository.PostRepository
import com.asharya.divinex.repository.UserRepository
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class EditProfileFragment : Fragment() {
    private lateinit var viewModel:EditProfileViewModel
    private lateinit var civEditProfileImage: CircleImageView
    private lateinit var etEmail: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var btnFinishEdit: Button
    private lateinit var tvEditUsername:TextView

    private val args by navArgs<EditProfileFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        // binding views
        civEditProfileImage = view.findViewById(R.id.civEditProfileImage)
        etEmail= view.findViewById(R.id.etEmail)
        rgGender= view.findViewById(R.id.rgGender)
        btnFinishEdit= view.findViewById(R.id.btnFinishEdit)
        tvEditUsername = view.findViewById(R.id.tvEditUsername)

        // setting values in view
        etEmail.setText(args.user.email)
        var imagePath = ServiceBuilder.loadImagePath() + args.user.profilePicture
        imagePath = imagePath.replace("\\", "/")
        Glide.with(requireActivity()).load(imagePath).into(civEditProfileImage)
        tvEditUsername.text = args.user.username


        // viewmodel
        val userDao = DivinexDB.getInstance(requireContext()).getUserDAO()
        val repository = UserRepository(userDao)
        viewModel = ViewModelProvider(
            this,
            EditProfileViewModelFactory(repository!!)
        ).get(EditProfileViewModel::class.java)


        // opening menu
        civEditProfileImage.setOnClickListener {
            loadPopUpMenu()
        }
        return view
    }

    private fun validate(): Boolean {
        when {
            TextUtils.isEmpty(etEmail.text) -> {
                etEmail.error = "Please Add a Caption"
                etEmail.requestFocus()
                return false
            }
            imageUrl == null -> {
                Toast.makeText(context, "Please Select an Image", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    private fun loadPopUpMenu() {
        val popupMenu = PopupMenu(context, civEditProfileImage)
        popupMenu.menuInflater.inflate(R.menu.gallery_camera, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuCamera -> openCamera()
                R.id.menuGallery -> openGallery()
            }
            true
        }
        popupMenu.show()
    }

    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = context?.contentResolver
                val cursor =
                    contentResolver?.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                civEditProfileImage.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                civEditProfileImage.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
        }

    }

    private fun bitmapToFile(
        bitmap: Bitmap,
        fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }
}
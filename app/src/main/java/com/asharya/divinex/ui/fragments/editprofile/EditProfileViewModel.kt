package com.asharya.divinex.ui.fragments.editprofile

import android.webkit.MimeTypeMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.repository.UserRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class EditProfileViewModel(private val repository: UserRepository): ViewModel() {

    private val _profileUpdated = MutableLiveData<Boolean>()
    val profileUpdated: LiveData<Boolean>
    get() = _profileUpdated

    init {
    }

    fun updateProfile(caption:String, image:String, postID: String) {
        viewModelScope.launch {
            val file = File(image)
            val extension = MimeTypeMap.getFileExtensionFromUrl(image)
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)

            val reqFile = file.asRequestBody(mimeType?.toMediaType())
            val body = MultipartBody.Part.createFormData("image",file.name, reqFile)
            val reqCaption = caption.toRequestBody("text/plain".toMediaType())
//            try {
//            } catch (ex: Exception) {
//                Log.i("AddPostViewModel", ex.toString())
//            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _profileUpdated.value = false
    }
}
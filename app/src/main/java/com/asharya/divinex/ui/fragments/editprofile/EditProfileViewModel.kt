package com.asharya.divinex.ui.fragments.editprofile

import android.util.Log
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
import java.lang.Exception

class EditProfileViewModel(private val repository: UserRepository) : ViewModel() {

    private val _profileUpdated = MutableLiveData<Boolean>()
    val profileUpdated: LiveData<Boolean>
        get() = _profileUpdated

    init {
    }

    fun updateProfile(email: String, image: String?, userID: String, gender: String) {
        viewModelScope.launch {
            if (image != null) {
                val file = File(image)
                val extension = MimeTypeMap.getFileExtensionFromUrl(image)
                val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)

                val reqFile = file.asRequestBody(mimeType?.toMediaType())
                val body = MultipartBody.Part.createFormData("image", file.name, reqFile)
                val reqEmail = email.toRequestBody("text/plain".toMediaType())
                val reqGender = gender.toRequestBody("text/plain".toMediaType())
                try {
                    val response = repository.updateProfileWithImage(body, userID, reqEmail, reqGender)
                    if (response.success == true)
                        _profileUpdated.value = true
                } catch (ex: Exception) {
                    Log.e("EditProfileViewModel", ex.toString())
                    _profileUpdated.value = false
                }
            } else {
                try {
                    val response = repository.updateProfile(userID, email, gender)
                    if (response.success == true)
                        _profileUpdated.value = true
                } catch (ex: Exception) {
                    Log.e("EditProfileViewModel", ex.toString())
                    _profileUpdated.value = false
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _profileUpdated.value = false
    }
}
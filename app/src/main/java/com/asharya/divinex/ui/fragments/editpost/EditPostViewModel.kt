package com.asharya.divinex.ui.fragments.editpost

import android.util.Log
import android.webkit.MimeTypeMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.repository.PostRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.lang.Exception

class EditPostViewModel(private val repository: PostRepository): ViewModel() {

    private val _postUpdated = MutableLiveData<Boolean>()
    val postUpdated : LiveData<Boolean>
    get() = _postUpdated
    init {
    }

    fun updatePost(caption:String, image:String, postID: String) {
        viewModelScope.launch {
            val file = File(image)
            val extension = MimeTypeMap.getFileExtensionFromUrl(image)
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)

            val reqFile = file.asRequestBody(mimeType?.toMediaType())
            val body = MultipartBody.Part.createFormData("image",file.name, reqFile)
            val reqCaption = caption.toRequestBody("text/plain".toMediaType())
            try {
                val response = repository.updatePost(reqCaption, body, postID)
                if (response.success == true)
                    _postUpdated.value = true
            } catch (ex: Exception) {
                _postUpdated.value = false
                Log.i("AddPostViewModel", ex.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _postUpdated.value = false
    }
}
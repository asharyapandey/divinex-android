package com.asharya.divinex.ui.fragments.addpost

import android.util.Log
import android.webkit.MimeTypeMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asharya.divinex.repository.PostRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception

class AddPostViewModel(private val repository: PostRepository): ViewModel() {

    private val _postAdded = MutableLiveData<Boolean>()
    val postAdded : LiveData<Boolean>
    get() = _postAdded
    init {
    }

    fun addPost(caption:String, image:String) {
        viewModelScope.launch {
            val file = File(image)
            val extension = MimeTypeMap.getFileExtensionFromUrl(image)
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)

            val reqFile = RequestBody.create(MediaType.parse(mimeType),file)
            val body = MultipartBody.Part.createFormData("image",file.name, reqFile)
            try {
                val response = repository.addPost(caption, body)
                if (response.success == true)
                    _postAdded.value = true
            } catch (ex: Exception) {
                _postAdded.value = false
                Log.i("AddPostViewModel", ex.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _postAdded.value = false
    }
}
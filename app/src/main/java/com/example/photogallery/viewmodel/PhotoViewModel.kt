package com.example.photogallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photogallery.R
import com.example.photogallery.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PhotoViewModel : ViewModel() {

    // MutableLiveData chứa danh sách ảnh
    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> = _photos

    private val _favoritePhotos = MutableStateFlow<List<Photo>>(emptyList())
    val favoritePhotos: StateFlow<List<Photo>> = _favoritePhotos
    // Khởi tạo danh sách ảnh mẫu
    init {
        loadPhotos()
    }

    private fun loadPhotos() {
        // Bạn có thể tải dữ liệu từ một API, database hoặc local resource
        _photos.value = listOf(
            Photo(R.drawable.pt1),
            Photo(R.drawable.pt2),
            Photo(R.drawable.pt3),
            Photo(R.drawable.pt4),
            Photo(R.drawable.pt5),
            Photo(R.drawable.pt6),
            Photo(R.drawable.pt7),
            Photo(R.drawable.pt8),
        )

    }

    // Hàm thêm ảnh mới
    fun addPhoto(photo: Photo) {
        val current = _photos.value?.toMutableList() ?: mutableListOf()
        current.add(photo)
        _photos.value = current
    }

    // Hàm xóa ảnh
    fun deletePhoto(photo: Photo) {
        val currentList = _photos.value?.toMutableList() ?: mutableListOf()
        currentList.remove(photo)
        _photos.value = currentList
    }
    fun addFavorite(photo: Photo) {
        _favoritePhotos.value = _favoritePhotos.value + photo
    }

    // Optionally, you can implement a remove function to un-favorite a photo
    fun removeFavorite(photo: Photo) {
        _favoritePhotos.value = _favoritePhotos.value.filter { it.resId != photo.resId }
    }

}

package com.example.watchera.Domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Watchera.Database.DatabaseHelper

class MainViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    // Load Banner Data
    fun loadBanner(): LiveData<List<BannerModel>> {
        val liveData = MutableLiveData<List<BannerModel>>()
        liveData.value = dbHelper.getAllBanners()
        return liveData
    }

    // Load Category Data
    fun loadCategory(): LiveData<List<CategoryModel>> {
        val liveData = MutableLiveData<List<CategoryModel>>()
        liveData.value = dbHelper.getAllCategories()
        return liveData
    }

    // Load Popular Items Data
    fun loadPopular(): LiveData<List<ItemsModel>> {
        val liveData = MutableLiveData<List<ItemsModel>>()
        liveData.value = dbHelper.getAllPopularItems()
        return liveData
    }
}
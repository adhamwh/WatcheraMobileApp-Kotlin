package com.example.Watchera.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.Watchera.Database.DatabaseHelper

import com.example.watchera.Domain.BannerModel
import com.example.watchera.Domain.CategoryModel
import com.example.watchera.Domain.ItemsModel

class MainRepository(private val dbHelper: DatabaseHelper) {

    fun loadBanner(): LiveData<List<BannerModel>> {
        val liveData = MutableLiveData<List<BannerModel>>()
        liveData.value = dbHelper.getAllBanners()
        return liveData
    }

    fun loadCategory(): LiveData<List<CategoryModel>> {
        val liveData = MutableLiveData<List<CategoryModel>>()
        liveData.value = dbHelper.getAllCategories()
        return liveData
    }

    fun loadPopular(): LiveData<List<ItemsModel>> {
        val liveData = MutableLiveData<List<ItemsModel>>()
        liveData.value = dbHelper.getAllPopularItems()
        return liveData
    }
}
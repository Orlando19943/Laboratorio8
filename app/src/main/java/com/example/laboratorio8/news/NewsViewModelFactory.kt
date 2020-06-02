package com.example.laboratorio8.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.laboratorio8.news.NewsViewModel

class NewsViewModelFactory (private val keyWord: String,private val points: String,private val author: String) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(keyWord,points,author) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
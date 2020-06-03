package com.example.laboratorio8.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.laboratorio8.network.HackerNewsApi
import com.example.laboratorio8.network.HackerNewsUser
import com.example.laboratorio8.network.NewsApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsViewModel (private val keyWord: String,private val points: String,private val author: String) : ViewModel() {

    private val _hackerNews = MutableLiveData<List<HackerNewsUser>>()
    val hackerNews: LiveData<List<HackerNewsUser>>
        get() = _hackerNews

    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus>
        get() = _status

    private val _currentHackerNew = MutableLiveData<HackerNewsUser>()
    val currentHackerNew: LiveData<HackerNewsUser>
        get() = _currentHackerNew

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        startStatus()
        getNews()
    }

    fun startStatus(){
        _status.value = NewsApiStatus.START
    }

    fun openRepoUrl(hackerNews2: HackerNewsUser){
        _currentHackerNew.value = hackerNews2
    }

    private fun getNews(){
        coroutineScope.launch {
            val newsDeferred = HackerNewsApi.retrofitService.getHackerNewsAsync(keyWord,points,author)
            try {
                _status.value = NewsApiStatus.LOADING
                val news = newsDeferred.await()
                _status.value = NewsApiStatus.DONE
                _hackerNews.value = news
            } catch (e: Exception){
                _status.value = NewsApiStatus.ERROR
                _hackerNews.value = emptyList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

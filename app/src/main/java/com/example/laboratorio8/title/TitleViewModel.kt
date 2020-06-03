package com.example.laboratorio8.title

import android.os.Debug
import android.util.Log
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
import java.lang.Exception



class TitleViewModel : ViewModel() {

    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus>
        get() = _status

    private val _viewNews = MutableLiveData<Boolean>()
    val viewNews: LiveData<Boolean>
        get () = _viewNews

    val keyWord = MutableLiveData<String>()


    val points = MutableLiveData<String>()


    val author = MutableLiveData<String>()


    private val _newsUser = MutableLiveData<HackerNewsUser>()
    val newsUser:LiveData<HackerNewsUser>
        get() = _newsUser

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        startStatus()
    }

    fun startStatus(){
        _status.value = NewsApiStatus.START
    }

    fun search(){
        coroutineScope.launch {
            val newsUserDeferred = HackerNewsApi.retrofitService.getPropertiesAsync(keyWord.value,"story",points.value,author.value)
            try {
                _status.value =
                    NewsApiStatus.LOADING
                val newsUser = newsUserDeferred.await()
                _status.value = NewsApiStatus.DONE
                _newsUser.value = newsUser
            }catch (e:Exception){
                _status.value =
                    NewsApiStatus.ERROR

            }
        }
    }
    fun actionViewNews() {
        _viewNews.value = true
    }

    fun viewNewsComplete() {
        _viewNews.value = false
    }
}

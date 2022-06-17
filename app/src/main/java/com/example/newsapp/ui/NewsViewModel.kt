package com.example.newsapp.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.newsapp.models.Message
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(
    app: Application,
    val newsRepository: NewsRepository
): AndroidViewModel(app)  {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    val readMessage: LiveData<List<Message>> = newsRepository.readDatabase().asLiveData()

    init {
        getBreakingNews()
    }

    fun getBreakingNews() = viewModelScope.launch {
        safeBreakingNewsCall()
    }

//    private fun offlineCacheRecipe(newsResponse: NewsResponse) {
//        //val message = Message(newsResponse.messages)
//        saveMessage(message)
//    }


    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun saveMessage(message: Message) = viewModelScope.launch {
        newsRepository.upsert(message)
    }

    fun updateBookMarked(isBookmarked : Boolean ,  id : String) = viewModelScope.launch {
        newsRepository.updateBookMarked(isBookmarked , id)
    }

    fun selectBookMarked() = newsRepository.selectBookMarked()

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteMessage(message: Message) = viewModelScope.launch {
        newsRepository.deleteMessage(message)
    }


    private suspend fun safeBreakingNewsCall() {
        breakingNews.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = newsRepository.getBreakingNews()
                breakingNews.postValue(handleBreakingNewsResponse(response))


                //offlineCacheRecipe(breakingNews.value!!.data)

            } else {
                breakingNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> breakingNews.postValue(Resource.Error("Network Failure"))
                else -> breakingNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

    }




}
package com.example.ermobrawl2.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ermobrawl2.network.domain.model.NewsArticle
import com.example.ermobrawl2.network.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun getNews(page: String = "", selectCategory: String = "") {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val currentNews = state.value.newsInfo?.results ?: emptyList()
                val news = newsRepository.getNewsList(selectCategory, page)
                val updNews = news.copy(
                    results = currentNews + news.results
                )
                _state.update { it.copy(newsInfo = updNews, isLoading = false) }
                Log.d("ПРИВЕТ", selectCategory)
            } catch (e: Exception) {
                Log.d("HomeViewModel", "Error: ${e.localizedMessage}")
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
    fun getNewsOnQuery(page: String = "", selectCategory: String = "") {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val encodedCategory = encodeForUrl(selectCategory)
                val currentNews = newsRepository.getNewsList("&qInTitle=$encodedCategory", page)
                _state.update { it.copy(newsInfo = currentNews, isLoading = false)}
                _state.update { it.copy(encodedCategory = "&qInTitle=$encodedCategory") }
            } catch (e: Exception) {
                Log.d("HomeViewModel", "Error: ${e.localizedMessage}")
                _state.update { it.copy(isLoading = false) }

            }
        }
    }
}

private fun encodeForUrl(value: String): String {
    return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
}

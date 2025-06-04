package com.example.ermobrawl2.ui.screen.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ermobrawl2.network.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject  constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(NewsState())
    val state = _state.asStateFlow()


    fun getNewsInfo(id: String) {
        viewModelScope.launch {
            try {
                val newsInfo = newsRepository.getNewsInfo(id)
                val selectedArticle = newsInfo.results.find { it.articleId == id }
                _state.update {
                    it.copy(
                        news = newsInfo.results,
                        selectedNews = selectedArticle
                    )
                }
                Log.d("ПОКА", id)
            } catch (e: Exception) {
                Log.d("NewsViewModel", "Error: ${e.localizedMessage}")
            }
        }
    }
}
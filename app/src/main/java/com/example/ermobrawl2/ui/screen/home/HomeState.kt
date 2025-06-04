package com.example.ermobrawl2.ui.screen.home

import com.example.ermobrawl2.network.domain.model.NewsArticle
import com.example.ermobrawl2.network.domain.model.NewsResponse

data class HomeState(
    val newsInfo: NewsResponse? = null,
    val isLoading: Boolean = false,
    val encodedCategory: String = ""
)
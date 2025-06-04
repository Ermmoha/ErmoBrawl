package com.example.ermobrawl2.ui.screen.news

import com.example.ermobrawl2.network.domain.model.NewsArticle

data class NewsState(
    val news: List<NewsArticle> = emptyList(),
    val selectedNews: NewsArticle? = null,
)


package com.example.ermobrawl2.network.domain.repository

import com.example.ermobrawl2.network.domain.model.NewsResponse

interface NewsRepository {
    suspend fun getNewsList(selectCategory: String, nextPage: String): NewsResponse
    suspend fun getNewsInfo(id: String): NewsResponse
}
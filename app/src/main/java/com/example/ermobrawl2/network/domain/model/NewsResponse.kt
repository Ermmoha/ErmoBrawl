package com.example.ermobrawl2.network.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val nextPage: String? = null,
    val results: List<NewsArticle> = emptyList()
)

@Serializable
data class NewsArticle(
    @SerialName("article_id")
    val articleId: String,
    val title: String,
    val link: String,
    val keywords: List<String>? = emptyList(),
    val creator: List<String>? = emptyList(),
    val description: String? = null,
    val content: String? = null,
    val pubDate: String,
    val pubDateTZ: String? = null,
    @SerialName("image_url")
    val imageUrl: String? = null,
    @SerialName("video_url")
    val videoUrl: String? = null,
    @SerialName("source_id")
    val sourceId: String,
    @SerialName("source_name")
    val sourceName: String? = null,
    @SerialName("source_priority")
    val sourcePriority: Int? = null,
    @SerialName("source_url")
    val sourceUrl: String? = null,
    @SerialName("source_icon")
    val sourceIconUrl: String? = null,
    val language: String,
    val country: List<String>? = emptyList(),
    val category: List<String>? = emptyList(),
    val sentiment: String? = null,
    val duplicate: Boolean? = false,
    val coin: List<String>? = emptyList()
)
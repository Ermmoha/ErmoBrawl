package com.example.ermobrawl2.network.data

import android.util.Log
import com.example.ermobrawl2.network.core.ApiConst
import com.example.ermobrawl2.network.domain.model.NewsResponse
import com.example.ermobrawl2.network.domain.repository.NewsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
) : NewsRepository {
    override suspend fun getNewsList(
        selectCategory: String,
        nextPage: String
    ): NewsResponse {

        val path =
            if(nextPage.isEmpty())
             ApiConst.BASE_URL + "&" + "language=ru" + selectCategory
        else if(nextPage.isNotEmpty())
            ApiConst.BASE_URL + "&" + "language=ru" + selectCategory + "&page=" + nextPage
        else ApiConst.BASE_URL
        Log.d("ПРИВЕТ", path)
        return httpClient.get(path).body()
    }

    override suspend fun getNewsInfo(id: String): NewsResponse {
        val path = ApiConst.BASE_URL + "&id=" + id
        return httpClient.get(path).body()
    }

}

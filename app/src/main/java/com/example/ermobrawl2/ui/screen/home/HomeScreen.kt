package com.example.ermobrawl2.ui.screen.home

import android.content.ClipData.Item
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ermobrawl2.R
import com.example.ermobrawl2.ui.components.CustomTopBar

@Composable
fun HomeScreen(
    category: String,
    viewModel: HomeViewModel,
    navigationOnCategory: () -> Unit,
    titleCategory: String,
    navigateOnBack: () -> Unit,
    navigationOnNews: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    var value by remember { mutableStateOf("") }
    val selectCategory = if (category == "") state.encodedCategory else category
    var isSearch by remember { mutableStateOf(false) }

    Content(
        state = state,
        getNextPage = { viewModel.getNews(it ?: "", selectCategory) },
        getUserQuery = {viewModel.getNewsOnQuery(selectCategory = it ?: "")},
        navigationOnCategory = navigationOnCategory,
        titleCategory = titleCategory,
        navigateOnBack = navigateOnBack,
        value = value,
        onValueChange = {value = it},
        navigationOnNews = navigationOnNews,
        onSearch = { isSearch = true},
        isSearch = isSearch,
        results = state.newsInfo?.totalResults ?: 0
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    state: HomeState,
    getNextPage: (String?) -> Unit,
    getUserQuery: (String?) -> Unit,
    navigationOnCategory: () -> Unit,
    titleCategory: String,
    navigateOnBack: () -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    navigationOnNews: (String) -> Unit,
    onSearch: () -> Unit,
    isSearch: Boolean,
    results: Int
) {

    val lazyState = rememberLazyListState()
    val isLoading = !lazyState.canScrollForward

    LaunchedEffect(lazyState.canScrollForward) {
        if (isLoading) {
            getNextPage(state.newsInfo?.nextPage)
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(247, 247, 249)),
        topBar = {
            if (titleCategory == "") {
                CustomTopBar(
                    value = value,
                    onValueChange = { onValueChange(it) },
                    onDone = {
                        getUserQuery(value)
                        onSearch()
                    }
                )
            } else
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = titleCategory)
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { navigateOnBack() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
        },
        floatingActionButton = {
            if (titleCategory == "")
                IconButton(
                    onClick = navigationOnCategory,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(80.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color(66, 170, 255),
                        contentColor = Color.White,
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.category2),
                        contentDescription = null,
                        modifier = Modifier
                            .size(45.dp)
                    )
                }
        }

    ) { contentPadding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(contentPadding)
                        .padding(horizontal = 15.dp, vertical = 0.dp)
                        .fillMaxSize(),
                    state = lazyState
                ) {
                    item {
                        if (isSearch && state.newsInfo != null) {
                            Text(
                                text = "По вашему запросы найдено: $results новости(ей)",
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.news702btrusbymeitalic))
                            )
                        }
                    }
                    state.newsInfo?.let {
                        items(it.results) { info ->
                            NewsCard(
                                painter = info.imageUrl,
                                title = info.title,
                                date = info.pubDate,
                                sourcePainter = info.sourceIconUrl,
                                sourceName = info.sourceName,
                                navigationOnNews = navigationOnNews,
                                id = info.articleId
                            )
                        }
                    }
                }
                AnimatedVisibility(
                    visible = isLoading,
                    enter = fadeIn() + slideInVertically { it / 2 },
                    exit = fadeOut() + slideOutVertically { it / 2 },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.White, shape = CircleShape)
                            .padding(12.dp),
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 3.dp,
                            color = Color(0xFF6200EE),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NewsCard(
    painter: String?,
    title: String,
    date: String,
    sourcePainter: String?,
    sourceName: String?,
    navigationOnNews: (String) -> Unit,
    id: String
) {
    Card(
        onClick = { navigationOnNews(id) },
        modifier = Modifier
            .height(230.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = painter ?: R.drawable.nophoto,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop

            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.2f),
                                Color.Black.copy(alpha = 0.5f),
                                Color.Black.copy(alpha = 0.7f),
                                Color.Black.copy(alpha = 0.9f)
                            )
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .background(
                        Color(0f, 0f, 0f, 0.7f),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(5.dp)
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = sourcePainter ?: R.drawable.nophoto,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .size(45.dp),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop

                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = sourceName ?: "Источник не известен",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.news702btrusbymeitalic))
                )
            }
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .align(Alignment.BottomCenter),
            ) {
                Text(
                    text = date,
                    fontSize = 17.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.news702btrusbymeitalic))
                )
                Text(
                    text = title,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily(Font(R.font.news702btrusbymebold)),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//private fun HomePreview() {
//    NewsCard(
//        painter = painterResource(R.drawable.example),
//        title = "nfjkreoflef"
//    )
//}
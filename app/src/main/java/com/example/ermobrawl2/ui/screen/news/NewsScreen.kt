package com.example.ermobrawl2.ui.screen.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ermobrawl2.R
import androidx.core.net.toUri

@Composable
fun NewsScreen(
    viewModel: NewsViewModel,
    navigateOnBack: () -> Unit,
    id: String
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(id) {
        viewModel.getNewsInfo(id)
    }

    if (state.selectedNews == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val news = state.selectedNews!!
        Content(
            sourcePainter = news.sourceIconUrl,
            sourceTitle = news.sourceName ?: "Источник не известен",
            navigateOnBack = navigateOnBack,
            title = news.title,
            painter = news.imageUrl,
            id = id,
            getNews = { viewModel.getNewsInfo(id) },
            discription = news.description ?: "Нет информации",
            date = news.pubDate,
            author = (news.creator ?: "Автор неизвестен").toString(),
            urlOnNews = news.link,
            urlSource = news.sourceUrl ?: "",
            context = context,
        )
    }
}


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Content(
        sourcePainter: String?,
        sourceTitle: String,
        navigateOnBack:() -> Unit,
        title: String,
        painter: String?,
        id: String,
        getNews: () -> Unit,
        discription: String,
        date: String,
        author: String,
        urlOnNews: String,
        urlSource: String,
        context: Context,
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(15.dp))
                                .clickable {
                                    if(urlSource != "") {
                                        val intent = Intent(Intent.ACTION_VIEW).apply {
                                            data = urlSource.toUri()
                                        }
                                        context.startActivity(intent)
                                    } else {
                                        Toast.makeText(context, "Некорректная или пустая ссылка", Toast.LENGTH_SHORT).show()
                                    }
                                },
                            horizontalArrangement = Arrangement.Center,
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
                                text = sourceTitle,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    },
                    modifier = Modifier
                        .shadow(
                            elevation = 10.dp
                        ),
                    navigationIcon = {
                        IconButton(
                            onClick = {navigateOnBack()}
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }            )
            }
        ) { contentPadding ->

            LaunchedEffect(id) {
                getNews()
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(contentPadding)
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(
                    text = title,
                    modifier = Modifier,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.news702btrusbymebold))
                )
                Spacer(modifier = Modifier.height(10.dp))
                AsyncImage(
                    model = painter ?: R.drawable.nophoto,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    thickness = 3.dp
                )
                Text(
                    text = discription,
                    fontFamily = FontFamily(Font(R.font.news702btrusbymeroman)),
                    color = Color.Black
                )
                    Text(
                        text = "Подробнее...",
                        modifier = Modifier
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = urlOnNews.toUri()
                                }
                                context.startActivity(intent)
                            },
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.news702btrusbymeroman)),
                        color = Color.Blue
                    )


                Spacer(modifier = Modifier.height(10.dp))
                Text(
                        text = author,
                        modifier = Modifier
                            .align(Alignment.End),
                        fontFamily = FontFamily(Font(R.font.news702btrusbymeitalic)),
                        fontSize = 13.sp
                    )
                    Text(
                        text = date,
                        modifier = Modifier
                            .align(Alignment.End),
                        fontFamily = FontFamily(Font(R.font.news702btrusbymeitalic)),
                        fontSize = 13.sp
                    )
            }
        }
    }


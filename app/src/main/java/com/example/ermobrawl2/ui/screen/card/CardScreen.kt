package com.example.ermobrawl2.ui.screen.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.ermobrawl2.R
import com.example.ermobrawl2.model.DataSource.categoryList

@Composable
fun CardScreen(
    navigateOnHome: (String, String) -> Unit,
    navigateOnBack: () -> Unit
) {
    Content(
        navigateOnHome = navigateOnHome,
        navigateOnBack = navigateOnBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    navigateOnHome: (String, String) -> Unit,
    navigateOnBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(247, 247, 249)),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Категории",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigateOnBack
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            items(categoryList) {
                    CustomCardCategory(
                        painter = it.painter,
                        title = it.title,
                        navigateOnHome = navigateOnHome,
                        tag = it.tag
                    )
            }
        }
    }
}

@Composable
private fun CustomCardCategory(
    painter: Int,
    title: String,
    navigateOnHome: (String, String) -> Unit,
    tag: String?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .layout { measurable, constraints ->
                val placeable = measurable.measure(
                    constraints.copy(
                        maxHeight = constraints.maxWidth
                    )
                )
                layout(placeable.width, placeable.height) {
                    placeable.place(0, 0)
                }
            },
        onClick = {
            if (tag != null) {
                navigateOnHome(tag, title)
            }
        },
        shape = RoundedCornerShape(25.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = painter
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop

            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)
                    )
            )
            Text(
                text = title,
                modifier = Modifier
                    .align(Alignment.Center),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.news702btrusbymebold)),
                color = Color.White
            )
        }
    }
}
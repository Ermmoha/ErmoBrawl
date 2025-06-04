package com.example.ermobrawl2.model

import androidx.compose.ui.res.painterResource
import com.example.ermobrawl2.R


object DataSource{
    val categoryList = listOf(
        CategoryModel("Бизнес", R.drawable.business, "&category=business"),
        CategoryModel("Криминал", R.drawable.crime, "&category=crime"),
        CategoryModel("Образовние" , R.drawable.education, "&category=education"),
        CategoryModel("Развлечение" , R.drawable.entertainment, "&category=entertainment"),
        CategoryModel("Окр. среда" , R.drawable.environment, "&category=environment"),
        CategoryModel("Здоровье" , R.drawable.health, "&category=health"),
        CategoryModel("lifestyle" , R.drawable.lifestyle, "&category=lifestyle"),
        CategoryModel("Политика" , R.drawable.politics, "&category=politics"),
        CategoryModel("Наука" , R.drawable.science, "&category=science"),
        CategoryModel("Спорт" , R.drawable.sports, "&category=sports"),
        CategoryModel("Технологии" , R.drawable.technology, "&category=technology"),
        CategoryModel("Топ" , R.drawable.top, "&category=top"),
        CategoryModel("Туризм" , R.drawable.tourism, "&category=tourism"),
        CategoryModel("Мир" , R.drawable.world, "&category=world"),
        CategoryModel("Другое" , R.drawable.other, "&category=other"),




        )
}
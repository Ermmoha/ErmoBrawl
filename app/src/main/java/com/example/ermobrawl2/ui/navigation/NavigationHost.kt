package com.example.ermobrawl2.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.ermobrawl2.ui.screen.card.CardScreen
import com.example.ermobrawl2.ui.screen.home.HomeScreen
import com.example.ermobrawl2.ui.screen.home.HomeViewModel
import com.example.ermobrawl2.ui.screen.news.NewsScreen
import com.example.ermobrawl2.ui.screen.news.NewsViewModel

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.Home("","")
    ) {
        composable<NavigationDestination.Home> { navBackStackEntry  ->
            val category: NavigationDestination.Home = navBackStackEntry.toRoute()
            val titleCategory: NavigationDestination.Home = navBackStackEntry.toRoute()
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = homeViewModel,
                category = category.selectCategory,
                titleCategory = titleCategory.titleCategory,
                navigationOnCategory = {
                    navController.navigate(NavigationDestination.Card)
                },
                navigateOnBack = {navController.popBackStack()},
                navigationOnNews = { id ->
                    navController.navigate(NavigationDestination.News(id = id))},
            )
        }
        composable<NavigationDestination.Card> {
            CardScreen(
                navigateOnHome = { category, titleCategory ->
                    navController.navigate(NavigationDestination.Home(selectCategory = category, titleCategory = titleCategory))
                },
                navigateOnBack = { navController.popBackStack() }
            )
        }
        composable<NavigationDestination.News> { navBackStackEntry ->
            val id: NavigationDestination.News = navBackStackEntry.toRoute()
            val newsViewModel = hiltViewModel<NewsViewModel>()
            Log.d("Navigation", "Arguments: ${navBackStackEntry.arguments}")
            Log.d("Navigation", "Parsed id: $id")
            NewsScreen(
                viewModel = newsViewModel,
                id = id.id,
                navigateOnBack = { navController.popBackStack() }
            )
        }
    }
}
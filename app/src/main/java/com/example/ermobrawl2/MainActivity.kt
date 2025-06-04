package com.example.ermobrawl2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.example.ermobrawl2.ui.navigation.NavigationHost
import com.example.ermobrawl2.ui.screen.home.HomeScreen
import com.example.ermobrawl2.ui.screen.home.HomeViewModel
import com.example.ermobrawl2.ui.theme.ErmoBrawl2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ErmoBrawl2Theme {
                NavigationHost()
                }
            }
        }
    }


package com.example.smartexpensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smartexpensetracker.ui.theme.SmartExpenseTrackerTheme
import com.example.smartexpensetracker.ui.screens.TransactionsScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SmartExpenseTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {

            NavigationBar {

                NavigationBarItem(
                    selected = currentRoute == "home",
                    onClick = {
                        navController.navigate("home")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = {
                        Text("Home")
                    }
                )

                NavigationBarItem(
                    selected = currentRoute == "transactions",
                    onClick = {
                        navController.navigate("transactions")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Transactions"
                        )
                    },
                    label = {
                        Text("Transactions")
                    }
                )

                NavigationBarItem(
                    selected = currentRoute == "budget",
                    onClick = {
                        navController.navigate("budget")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Wallet,
                            contentDescription = "Budget"
                        )
                    },
                    label = {
                        Text("Budget")
                    }
                )

                NavigationBarItem(
                    selected = currentRoute == "insights",
                    onClick = {
                        navController.navigate("insights")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "Insights"
                        )
                    },
                    label = {
                        Text("Insights")
                    }
                )

                NavigationBarItem(
                    selected = currentRoute == "profile",
                    onClick = {
                        navController.navigate("profile")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile"
                        )
                    },
                    label = {
                        Text("Profile")
                    }
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("home") {
                HomeScreen()
            }

            composable("transactions") {
                TransactionsScreen()
            }

            composable("budget") {
                BudgetScreen()
            }

            composable("insights") {
                InsightsScreen()
            }

            composable("profile") {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Home")
    }
}


@Composable
fun BudgetScreen() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Budget")
    }
}

@Composable
fun InsightsScreen() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Insights")
    }
}

@Composable
fun ProfileScreen() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Profile")
    }
}
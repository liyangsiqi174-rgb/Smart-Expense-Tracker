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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import com.example.smartexpensetracker.ui.screens.BudgetScreen
import com.example.smartexpensetracker.ui.screens.InsightsScreen
import com.example.smartexpensetracker.ui.screens.ProfileScreen

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

    val income = AppState.totalIncome()
    val expenses = AppState.totalExpenses()
    val balance = AppState.balance()
    val budget = AppState.budget.value
    val remainingBudget = AppState.remainingBudget()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Smart Expense Tracker",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Text(
            text = "Current Balance",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "$${"%.2f".format(balance)}",
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text("Income")
                Text(
                    "$${"%.2f".format(income)}",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Column {
                Text("Expenses")
                Text(
                    "$${"%.2f".format(expenses)}",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Text(
            text = "Budget",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Monthly Budget: $${"%.2f".format(budget)}"
        )

        Text(
            text = "Remaining: $${"%.2f".format(remainingBudget)}"
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Text(
            text = "Recent Transactions",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        if (AppState.transactions.isEmpty()) {

            Text("No transactions yet")

        } else {

            AppState.transactions
                .takeLast(5)
                .reversed()
                .forEach { transaction ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {

                        Column {
                            Text(transaction.description)
                            Text(transaction.category)
                        }

                        Text(
                            text =
                                if (transaction.type == "income") {
                                    "+$${"%.2f".format(transaction.amount)}"
                                } else {
                                    "-$${"%.2f".format(transaction.amount)}"
                                }
                        )
                    }
                }
        }
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
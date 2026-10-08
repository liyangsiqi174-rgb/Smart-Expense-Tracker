package com.example.smartexpensetracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartexpensetracker.AppState
import com.example.smartexpensetracker.data.Transaction
import java.time.LocalDate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator

@Composable
fun InsightsScreen() {

    var selectedPeriod by remember {
        mutableStateOf("Month")
    }

    val today = LocalDate.now()

    val filteredTransactions: List<Transaction> =
        when (selectedPeriod) {

            "Week" -> {
                val startDate = today.minusDays(6)

                AppState.transactions.filter { transaction ->

                    if (transaction.date.isBlank()) {
                        false
                    } else {
                        val transactionDate =
                            LocalDate.parse(transaction.date)

                        !transactionDate.isBefore(startDate) &&
                                !transactionDate.isAfter(today)
                    }
                }
            }

            "Year" -> {
                AppState.transactions.filter { transaction ->

                    if (transaction.date.isBlank()) {
                        false
                    } else {
                        val transactionDate =
                            LocalDate.parse(transaction.date)

                        transactionDate.year == today.year
                    }
                }
            }

            else -> {
                // Month
                AppState.transactions.filter { transaction ->

                    if (transaction.date.isBlank()) {
                        false
                    } else {
                        val transactionDate =
                            LocalDate.parse(transaction.date)

                        transactionDate.year == today.year &&
                                transactionDate.month == today.month
                    }
                }
            }
        }

    val income = filteredTransactions
        .filter {
            it.type == "income"
        }
        .sumOf {
            it.amount
        }

    val expenses = filteredTransactions
        .filter {
            it.type == "expense"
        }
        .sumOf {
            it.amount
        }

    val balance = income - expenses

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Insights"
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = {
                    selectedPeriod = "Week"
                }
            ) {
                Text("Week")
            }

            Button(
                onClick = {
                    selectedPeriod = "Month"
                }
            ) {
                Text("Month")
            }

            Button(
                onClick = {
                    selectedPeriod = "Year"
                }
            ) {
                Text("Year")
            }
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Text(
            text = "Showing: $selectedPeriod"
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
                    "$${"%.2f".format(income)}"
                )
            }

            Column {
                Text("Expenses")

                Text(
                    "$${"%.2f".format(expenses)}"
                )
            }
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Text("Balance")

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            "$${"%.2f".format(balance)}"
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Text("Spending by Category")

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        val categories = filteredTransactions
            .filter {
                it.type == "expense"
            }
            .groupBy {
                it.category
            }

        if (categories.isEmpty()) {

            Text(
                text = "No expense data for this period"
            )

        } else {

            categories.forEach { (category, transactions) ->

                val total = transactions.sumOf {
                    it.amount
                }

                val progress = if (expenses > 0) {
                    (total / expenses).toFloat()
                } else {
                    0f
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(category)

                        Text(
                            "$${"%.2f".format(total)}"
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )
                }
            }
        }
    }
}
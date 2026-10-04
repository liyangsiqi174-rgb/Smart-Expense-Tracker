package com.example.smartexpensetracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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


@Composable
fun TransactionsScreen() {

    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("expense") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Transactions"
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = {
                Text("Amount")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = {
                Text("Category")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = {
                Text("Description")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Button(
                onClick = {
                    type = "income"
                }
            ) {
                Text("Income")
            }

            Button(
                onClick = {
                    type = "expense"
                }
            ) {
                Text("Expense")
            }
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Button(
            onClick = {

                val transactionAmount =
                    amount.toDoubleOrNull()

                if (
                    transactionAmount != null &&
                    transactionAmount > 0 &&
                    category.isNotBlank()
                ) {

                    AppState.addTransaction(
                        Transaction(
                            id = System.currentTimeMillis(),
                            amount = transactionAmount,
                            type = type,
                            category = category,
                            description = description,
                            date = ""
                        )
                    )

                    amount = ""
                    category = ""
                    description = ""
                    type = "expense"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Transaction")
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Transaction List"
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        LazyColumn {

            items(
                AppState.transactions
            ) { transaction ->

                TransactionItem(
                    transaction = transaction
                )
            }
        }
    }
}

@Composable
fun TransactionItem(
    transaction: Transaction
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {

            Text(
                text = transaction.description
            )

            Text(
                text = transaction.category
            )
        }

        Text(
            text = if (transaction.type == "income") {
                "+$%.2f".format(transaction.amount)
            } else {
                "-$%.2f".format(transaction.amount)
            }
        )
    }
}
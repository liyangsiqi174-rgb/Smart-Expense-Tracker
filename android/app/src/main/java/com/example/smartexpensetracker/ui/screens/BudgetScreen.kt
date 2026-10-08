package com.example.smartexpensetracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun BudgetScreen() {

    var inputBudget by remember {
        mutableStateOf("")
    }

    val budget = AppState.budget.value
    val totalExpenses = AppState.totalExpenses()
    val remainingBudget = AppState.remainingBudget()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Budget"
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Text(
            text = "Set Monthly Budget"
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        OutlinedTextField(
            value = inputBudget,
            onValueChange = {
                inputBudget = it
            },
            label = {
                Text("Enter budget")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Button(
            onClick = {

                val amount = inputBudget.toDoubleOrNull()

                if (amount != null && amount >= 0) {
                    AppState.updateBudget(amount)
                    inputBudget = ""
                }
            }
        ) {
            Text("Save Budget")
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Text("Budget Summary")

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Monthly Budget: $${"%.2f".format(budget)}"
        )

        Text(
            text = "Total Expenses: $${"%.2f".format(totalExpenses)}"
        )

        Text(
            text = "Remaining Budget: $${"%.2f".format(remainingBudget)}"
        )
    }
}
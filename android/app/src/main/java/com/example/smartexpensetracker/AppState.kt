package com.example.smartexpensetracker

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.smartexpensetracker.data.Transaction

object AppState {

    val transactions = mutableStateListOf<Transaction>()

    val budget = mutableStateOf(0.0)

    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }

    fun deleteTransaction(id: Long) {
        transactions.removeAll {
            it.id == id
        }
    }

    fun updateTransaction(
        id: Long,
        amount: Double,
        category: String,
        description: String,
        type: String
    ) {
        val index = transactions.indexOfFirst {
            it.id == id
        }

        if (index != -1) {
            transactions[index] = transactions[index].copy(
                amount = amount,
                category = category,
                description = description,
                type = type
            )
        }
    }

    fun updateBudget(amount: Double) {
        budget.value = amount
    }

    fun totalIncome(): Double {
        return transactions
            .filter { it.type == "income" }
            .sumOf { it.amount }
    }

    fun totalExpenses(): Double {
        return transactions
            .filter { it.type == "expense" }
            .sumOf { it.amount }
    }

    fun balance(): Double {
        return totalIncome() - totalExpenses()
    }

    fun remainingBudget(): Double {
        return budget.value - totalExpenses()
    }
}
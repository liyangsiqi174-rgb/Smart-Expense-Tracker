import { useState } from "react";
import {
    getTransactions,
    getBudget
} from "../services/storage";

function Dashboard() {
    const [transactions] = useState(
        getTransactions()
    );

    const [budget] = useState(
        getBudget()
    );

    const totalIncome = transactions
        .filter(
            transaction => transaction.type === "income"
        )
        .reduce(
            (total, transaction) =>
                total + transaction.amount,
            0
        );

    const totalExpenses = transactions
        .filter(
            transaction => transaction.type === "expense"
        )
        .reduce(
            (total, transaction) =>
                total + transaction.amount,
            0
        );

    const balance = totalIncome - totalExpenses;

    const remainingBudget = budget - totalExpenses;

    const recentTransactions = [...transactions]
        .reverse()
        .slice(0, 5);

    return (
        <div>
            <h1>Dashboard</h1>

            <hr />

            <h2>Financial Summary</h2>

            <p>
                Total Income: $
                {totalIncome.toFixed(2)}
            </p>

            <p>
                Total Expenses: $
                {totalExpenses.toFixed(2)}
            </p>

            <p>
                Balance: $
                {balance.toFixed(2)}
            </p>

            <hr />

            <h2>Budget</h2>

            <p>
                Monthly Budget: $
                {budget.toFixed(2)}
            </p>

            <p>
                Remaining Budget: $
                {remainingBudget.toFixed(2)}
            </p>

            <hr />

            <h2>Recent Transactions</h2>

            {recentTransactions.length === 0 ? (
                <p>No transactions yet.</p>
            ) : (
                recentTransactions.map(transaction => (
                    <div key={transaction.id}>
                        <p>
                            {transaction.type} | $
                            {transaction.amount.toFixed(2)} |{" "}
                            {transaction.category} |{" "}
                            {transaction.description}
                        </p>
                    </div>
                ))
            )}
        </div>
    );
}

export default Dashboard;
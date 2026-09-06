import { useState } from "react";
import {
    getBudget,
    saveBudget,
    getTransactions
} from "../services/storage";

function Budget() {
    const [budget, setBudget] = useState(getBudget());

    const [inputBudget, setInputBudget] = useState(
        getBudget()
    );

    const [transactions] = useState(
        getTransactions()
    );

    function handleSaveBudget() {
        if (!inputBudget || Number(inputBudget) < 0) {
            alert("Please enter a valid budget.");
            return;
        }

        saveBudget(Number(inputBudget));

        setBudget(Number(inputBudget));
    }

    const totalExpenses = transactions
        .filter(
            transaction => transaction.type === "expense"
        )
        .reduce(
            (total, transaction) =>
                total + transaction.amount,
            0
        );

    const remainingBudget = budget - totalExpenses;

    return (
        <div>
            <h1>Budget</h1>

            <div>
                <h2>Set Monthly Budget</h2>

                <input
                    type="number"
                    placeholder="Enter budget"
                    value={inputBudget}
                    onChange={(e) =>
                        setInputBudget(e.target.value)
                    }
                />

                <button onClick={handleSaveBudget}>
                    Save Budget
                </button>
            </div>

            <hr />

            <div>
                <h2>Budget Summary</h2>

                <p>
                    Monthly Budget: $
                    {budget.toFixed(2)}
                </p>

                <p>
                    Total Expenses: $
                    {totalExpenses.toFixed(2)}
                </p>

                <p>
                    Remaining Budget: $
                    {remainingBudget.toFixed(2)}
                </p>
            </div>
        </div>
    );
}

export default Budget;
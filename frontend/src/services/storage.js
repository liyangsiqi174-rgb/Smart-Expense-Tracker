const TRANSACTIONS_KEY = "transactions";
const BUDGET_KEY = "budget";

export function getTransactions() {
    const data = localStorage.getItem(TRANSACTIONS_KEY);

    if (!data) {
        return [];
    }

    return JSON.parse(data);
}

export function saveTransactions(transactions) {
    localStorage.setItem(
        TRANSACTIONS_KEY,
        JSON.stringify(transactions)
    );
}

export function addTransaction(transaction) {
    const transactions = getTransactions();

    const newTransaction = {
        id: Date.now(),
        ...transaction
    };

    transactions.push(newTransaction);

    saveTransactions(transactions);

    return newTransaction;
}

export function updateTransaction(id, updatedTransaction) {
    const transactions = getTransactions();

    const updatedTransactions = transactions.map(
        transaction => {
            if (transaction.id === id) {
                return {
                    ...transaction,
                    ...updatedTransaction
                };
            }

            return transaction;
        }
    );

    saveTransactions(updatedTransactions);
}

export function deleteTransaction(id) {
    const transactions = getTransactions();

    const updatedTransactions = transactions.filter(
        transaction => transaction.id !== id
    );

    saveTransactions(updatedTransactions);
}

export function getBudget() {
    const budget = localStorage.getItem(BUDGET_KEY);

    if (!budget) {
        return 0;
    }

    return Number(budget);
}

export function saveBudget(amount) {
    localStorage.setItem(
        BUDGET_KEY,
        amount.toString()
    );
}
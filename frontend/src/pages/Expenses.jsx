import { useState } from "react";
import {
    getTransactions,
    addTransaction,
    updateTransaction,
    deleteTransaction
} from "../services/storage";

function Expenses() {

    const [transactions, setTransactions] = useState(
        getTransactions()
    );

    // Form states
    const [amount, setAmount] = useState("");
    const [type, setType] = useState("expense");
    const [category, setCategory] = useState("");
    const [description, setDescription] = useState("");

    // Default date = today
    const [date, setDate] = useState(
        new Date().toISOString().split("T")[0]
    );

    const [editingId, setEditingId] = useState(null);

    // Search and filter states
    const [searchTerm, setSearchTerm] = useState("");
    const [filterType, setFilterType] = useState("all");
    const [filterCategory, setFilterCategory] = useState("all");

    // Date filter states
    const [fromDate, setFromDate] = useState("");
    const [toDate, setToDate] = useState("");


    function handleAddTransaction() {

        if (!amount || !category || !date) {
            alert("Please enter amount, category and date.");
            return;
        }

        const transaction = addTransaction({
            amount: Number(amount),
            type: type,
            category: category,
            description: description,
            date: date
        });

        setTransactions([
            ...transactions,
            transaction
        ]);

        clearForm();
    }


    function handleEditTransaction(transaction) {

        setEditingId(transaction.id);

        setAmount(transaction.amount);
        setType(transaction.type);
        setCategory(transaction.category);
        setDescription(transaction.description);

        setDate(
            transaction.date.split("T")[0]
        );
    }


    function handleUpdateTransaction() {

        if (!amount || !category || !date) {
            alert("Please enter amount, category and date.");
            return;
        }

        const updatedTransaction = {
            amount: Number(amount),
            type: type,
            category: category,
            description: description,
            date: date
        };

        updateTransaction(
            editingId,
            updatedTransaction
        );

        setTransactions(
            transactions.map(transaction => {

                if (transaction.id === editingId) {

                    return {
                        ...transaction,
                        ...updatedTransaction
                    };

                }

                return transaction;

            })
        );

        clearForm();
    }


    function handleDeleteTransaction(id) {

        deleteTransaction(id);

        setTransactions(
            transactions.filter(
                transaction =>
                    transaction.id !== id
            )
        );

    }


    function clearForm() {

        setAmount("");
        setType("expense");
        setCategory("");
        setDescription("");

        setDate(
            new Date()
                .toISOString()
                .split("T")[0]
        );

        setEditingId(null);

    }


    // Get unique categories

    const categories = [

        ...new Set(
            transactions.map(
                transaction =>
                    transaction.category
            )
        )

    ];


    // Search and filter transactions

    const filteredTransactions =
        transactions.filter(transaction => {

            const matchesSearch =

                transaction.category
                    .toLowerCase()
                    .includes(
                        searchTerm.toLowerCase()
                    )

                ||

                transaction.description
                    .toLowerCase()
                    .includes(
                        searchTerm.toLowerCase()
                    );


            const matchesType =

                filterType === "all"

                ||

                transaction.type === filterType;


            const matchesCategory =

                filterCategory === "all"

                ||

                transaction.category ===
                filterCategory;


            // Date filtering

            const transactionDate =
                transaction.date.split("T")[0];


            const matchesFromDate =

                !fromDate

                ||

                transactionDate >= fromDate;


            const matchesToDate =

                !toDate

                ||

                transactionDate <= toDate;


            return (

                matchesSearch &&

                matchesType &&

                matchesCategory &&

                matchesFromDate &&

                matchesToDate

            );

        });


    // Calculate totals

    const totalIncome = transactions

        .filter(
            transaction =>
                transaction.type === "income"
        )

        .reduce(
            (total, transaction) =>
                total + transaction.amount,
            0
        );


    const totalExpenses = transactions

        .filter(
            transaction =>
                transaction.type === "expense"
        )

        .reduce(
            (total, transaction) =>
                total + transaction.amount,
            0
        );


    const balance =
        totalIncome - totalExpenses;


    return (

        <div>

            <h1>Expenses</h1>


            {/* Add / Edit Transaction */}

            <div>

                <input

                    type="number"

                    placeholder="Amount"

                    value={amount}

                    onChange={(e) =>
                        setAmount(e.target.value)
                    }

                />


                <select

                    value={type}

                    onChange={(e) =>
                        setType(e.target.value)
                    }

                >

                    <option value="expense">
                        Expense
                    </option>

                    <option value="income">
                        Income
                    </option>

                </select>


                <input

                    type="text"

                    placeholder="Category"

                    value={category}

                    onChange={(e) =>
                        setCategory(e.target.value)
                    }

                />


                <input

                    type="text"

                    placeholder="Description"

                    value={description}

                    onChange={(e) =>
                        setDescription(e.target.value)
                    }

                />


                {/* Date */}

                <input

                    type="date"

                    value={date}

                    onChange={(e) =>
                        setDate(e.target.value)
                    }

                />


                {editingId === null ? (

                    <button
                        onClick={
                            handleAddTransaction
                        }
                    >
                        Add Transaction
                    </button>

                ) : (

                    <>

                        <button
                            onClick={
                                handleUpdateTransaction
                            }
                        >
                            Save Changes
                        </button>


                        <button
                            onClick={clearForm}
                        >
                            Cancel
                        </button>

                    </>

                )}

            </div>


            <hr />


            {/* Summary */}

            <div>

                <h2>Summary</h2>


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

            </div>


            <hr />


            {/* Search and Filter */}

            <h2>
                Search and Filter
            </h2>


            <input

                type="text"

                placeholder="
                Search category or description
                "

                value={searchTerm}

                onChange={(e) =>
                    setSearchTerm(e.target.value)
                }

            />


            {/* Type Filter */}

            <select

                value={filterType}

                onChange={(e) =>
                    setFilterType(e.target.value)
                }

            >

                <option value="all">
                    All Types
                </option>

                <option value="income">
                    Income
                </option>

                <option value="expense">
                    Expense
                </option>

            </select>


            {/* Category Filter */}

            <select

                value={filterCategory}

                onChange={(e) =>
                    setFilterCategory(e.target.value)
                }

            >

                <option value="all">
                    All Categories
                </option>


                {categories.map(category => (

                    <option
                        key={category}
                        value={category}
                    >

                        {category}

                    </option>

                ))}

            </select>


            <br />

            <br />


            {/* From Date */}

            <label>
                From Date:
            </label>

            <input

                type="date"

                value={fromDate}

                onChange={(e) =>
                    setFromDate(e.target.value)
                }

            />


            {/* To Date */}

            <label>
                To Date:
            </label>

            <input

                type="date"

                value={toDate}

                onChange={(e) =>
                    setToDate(e.target.value)
                }

            />


            <hr />


            {/* Transactions */}

            <h2>
                Transactions
            </h2>


            {filteredTransactions.length === 0 ? (

                <p>
                    No transactions found.
                </p>

            ) : (

                filteredTransactions.map(
                    transaction => (

                        <div
                            key={transaction.id}
                        >

                            <p>

                                {transaction.type}

                                {" | $"}

                                {transaction.amount
                                    .toFixed(2)}

                                {" | "}

                                {transaction.category}

                                {" | "}

                                {transaction.description}

                                {" | "}

                                {transaction.date
                                    .split("T")[0]}

                            </p>


                            <button

                                onClick={() =>
                                    handleEditTransaction(
                                        transaction
                                    )
                                }

                            >
                                Edit
                            </button>


                            <button

                                onClick={() =>
                                    handleDeleteTransaction(
                                        transaction.id
                                    )
                                }

                            >
                                Delete
                            </button>

                        </div>

                    )

                )

            )}

        </div>

    );

}

export default Expenses;
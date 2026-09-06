import { useState } from "react";
import {
  View,
  Text,
  TextInput,
  Pressable,
  StyleSheet,
  FlatList,
} from "react-native";

type Transaction = {
  id: string;
  description: string;
  amount: number;
  type: "income" | "expense";
};

export default function HomeScreen() {
  const [description, setDescription] = useState("");
  const [amount, setAmount] = useState("");
  const [type, setType] = useState<"income" | "expense">("expense");

  const [transactions, setTransactions] = useState<Transaction[]>([]);

  const addTransaction = () => {
    const transactionAmount = Number(amount);

    if (description.trim() === "" || transactionAmount <= 0) {
      return;
    }

    const newTransaction: Transaction = {
      id: Date.now().toString(),
      description: description,
      amount: transactionAmount,
      type: type,
    };

    setTransactions([...transactions, newTransaction]);

    setDescription("");
    setAmount("");
  };

  const income = transactions
    .filter((transaction) => transaction.type === "income")
    .reduce((total, transaction) => total + transaction.amount, 0);

  const expenses = transactions
    .filter((transaction) => transaction.type === "expense")
    .reduce((total, transaction) => total + transaction.amount, 0);

  const balance = income - expenses;

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Smart Expense Tracker</Text>

      <Text style={styles.label}>Current Balance</Text>

      <Text style={styles.balance}>
        ${balance.toFixed(2)}
      </Text>

      <View style={styles.summary}>
        <View>
          <Text style={styles.label}>Income</Text>

          <Text style={styles.amount}>
            ${income.toFixed(2)}
          </Text>
        </View>

        <View>
          <Text style={styles.label}>Expenses</Text>

          <Text style={styles.amount}>
            ${expenses.toFixed(2)}
          </Text>
        </View>
      </View>

      <Text style={styles.sectionTitle}>
        Add Transaction
      </Text>

      <TextInput
        style={styles.input}
        placeholder="Description"
        value={description}
        onChangeText={setDescription}
      />

      <TextInput
        style={styles.input}
        placeholder="Amount"
        keyboardType="numeric"
        value={amount}
        onChangeText={setAmount}
      />

      <View style={styles.typeContainer}>
        <Pressable
          style={[
            styles.typeButton,
            type === "income" && styles.selectedButton,
          ]}
          onPress={() => setType("income")}
        >
          <Text>Income</Text>
        </Pressable>

        <Pressable
          style={[
            styles.typeButton,
            type === "expense" && styles.selectedButton,
          ]}
          onPress={() => setType("expense")}
        >
          <Text>Expense</Text>
        </Pressable>
      </View>

      <Pressable
        style={styles.addButton}
        onPress={addTransaction}
      >
        <Text style={styles.addButtonText}>
          Add Transaction
        </Text>
      </Pressable>

      <Text style={styles.sectionTitle}>
        Recent Transactions
      </Text>

      <FlatList
        data={transactions}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => (
          <View style={styles.transaction}>
            <Text>{item.description}</Text>

            <Text>
              {item.type === "income" ? "+" : "-"}$
              {item.amount.toFixed(2)}
            </Text>
          </View>
        )}
        ListEmptyComponent={
          <Text>No transactions yet</Text>
        }
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 25,
    paddingTop: 70,
  },

  title: {
    fontSize: 28,
    fontWeight: "bold",
    marginBottom: 30,
  },

  label: {
    fontSize: 16,
  },

  balance: {
    fontSize: 38,
    fontWeight: "bold",
    marginTop: 5,
    marginBottom: 25,
  },

  summary: {
    flexDirection: "row",
    justifyContent: "space-between",
    marginBottom: 30,
  },

  amount: {
    fontSize: 22,
    fontWeight: "bold",
    marginTop: 5,
  },

  sectionTitle: {
    fontSize: 20,
    fontWeight: "bold",
    marginTop: 20,
    marginBottom: 15,
  },

  input: {
    borderWidth: 1,
    padding: 12,
    marginBottom: 10,
  },

  typeContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    marginBottom: 15,
  },

  typeButton: {
    width: "48%",
    padding: 12,
    alignItems: "center",
    borderWidth: 1,
  },

  selectedButton: {
    backgroundColor: "#cccccc",
  },

  addButton: {
    padding: 15,
    alignItems: "center",
    backgroundColor: "#333333",
  },

  addButtonText: {
    color: "white",
    fontWeight: "bold",
  },

  transaction: {
    flexDirection: "row",
    justifyContent: "space-between",
    paddingVertical: 12,
    borderBottomWidth: 1,
  },
});
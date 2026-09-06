import { View, Text, StyleSheet } from "react-native";

export default function HomeScreen() {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Smart Expense Tracker</Text>

      <Text style={styles.balanceLabel}>
        Current Balance
      </Text>

      <Text style={styles.balance}>
        $0.00
      </Text>

      <Text style={styles.subtitle}>
        No transactions yet
      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },

  title: {
    fontSize: 28,
    fontWeight: "bold",
    marginBottom: 40,
  },

  balanceLabel: {
    fontSize: 18,
  },

  balance: {
    fontSize: 40,
    fontWeight: "bold",
    marginTop: 10,
  },

  subtitle: {
    fontSize: 16,
    marginTop: 30,
  },
});
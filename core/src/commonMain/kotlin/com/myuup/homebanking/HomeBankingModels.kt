package com.myuup.homebanking

enum class HomeBankingDirection {
    INCOMING, OUTGOING
}

enum class HomeBankingTone {
    PRIMARY, SECONDARY, NEUTRAL, ERROR, SUCCESS
}

data class HomeBankingAccount(
    val id: String,
    val name: String,
    val balance: Double,
    val currency: String
)

data class HomeBankingTransaction(
    val id: String,
    val title: String,
    val amount: Double,
    val date: String,
    val direction: HomeBankingDirection,
    val category: String = "General"
)

data class HomeBankingMovementPoint(
    val date: String,
    val balance: Double
)

data class HomeBankingScheduledPayment(
    val id: String,
    val name: String,
    val amount: Double,
    val dueDate: String
)

data class HomeBankingSnapshot(
    val totalBalance: Double,
    val currency: String,
    val accounts: List<HomeBankingAccount>,
    val recentTransactions: List<HomeBankingTransaction>,
    val scheduledPayments: List<HomeBankingScheduledPayment>,
    val balanceHistory: List<HomeBankingMovementPoint>
)

fun getMockSnapshot() = HomeBankingSnapshot(
    totalBalance = 12500.50,
    currency = "EUR",
    accounts = listOf(
        HomeBankingAccount("ACC-001", "Main Savings", 8500.0, "EUR"),
        HomeBankingAccount("ACC-002", "Daily Spending", 4000.5, "EUR")
    ),
    recentTransactions = listOf(
        HomeBankingTransaction("TXN-001", "Supermarket", 45.30, "2023-10-25", HomeBankingDirection.OUTGOING, "Shopping"),
        HomeBankingTransaction("TXN-002", "Salary", 3200.0, "2023-10-24", HomeBankingDirection.INCOMING, "Work"),
        HomeBankingTransaction("TXN-003", "Netflix", 15.99, "2023-10-23", HomeBankingDirection.OUTGOING, "Entertainment")
    ),
    scheduledPayments = listOf(
        HomeBankingScheduledPayment("SCH-001", "Rent", 1200.0, "2023-11-01"),
        HomeBankingScheduledPayment("SCH-002", "Gym", 50.0, "2023-11-05")
    ),
    balanceHistory = listOf(
        HomeBankingMovementPoint("2023-10-20", 9500.0),
        HomeBankingMovementPoint("2023-10-25", 12500.50)
    )
)

package com.myuup.homebanking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun HomeBankingScreenPreview() {
    MaterialTheme {
        HomeBankingContent(snapshot = getMockSnapshot())
    }
}

@Composable
fun HomeBankingScreen(viewModel: HomeBankingViewModel = viewModel { HomeBankingViewModel() }) {
    val snapshot by viewModel.uiState.collectAsState()
    HomeBankingContent(snapshot)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBankingContent(snapshot: HomeBankingSnapshot) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Home Banking") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                BalanceSection(snapshot.totalBalance, snapshot.currency)
            }
            item {
                SectionTitle("Your Accounts")
            }
            items(snapshot.accounts) { account ->
                AccountItem(account)
            }
            item {
                SectionTitle("Quick Actions")
            }
            item {
                QuickActionsRow()
            }
            item {
                SectionTitle("Scheduled Payments")
            }
            items(snapshot.scheduledPayments) { payment ->
                ScheduledPaymentItem(payment)
            }
            item {
                SectionTitle("Recent Transactions")
            }
            items(snapshot.recentTransactions) { transaction ->
                TransactionItem(transaction)
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun BalanceSection(balance: Double, currency: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Total Balance", style = MaterialTheme.typography.labelMedium)
            Text(
                text = "$currency $balance",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun AccountItem(account: HomeBankingAccount) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(account.name, style = MaterialTheme.typography.titleMedium)
                Text("ID: ${account.id}", style = MaterialTheme.typography.bodySmall)
            }
            Text(
                "${account.currency} ${account.balance}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun QuickActionsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val actions = listOf("Transfer", "Pay Bill", "Top Up", "Card")
        actions.forEach { action ->
            Button(
                onClick = {},
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(4.dp)
            ) {
                Text(action, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
fun ScheduledPaymentItem(payment: HomeBankingScheduledPayment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(payment.name, style = MaterialTheme.typography.titleMedium)
                Text("Due: ${payment.dueDate}", style = MaterialTheme.typography.bodySmall)
            }
            Text(
                "- $${payment.amount}",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TransactionItem(transaction: HomeBankingTransaction) {
    ListItem(
        headlineContent = { Text(transaction.title) },
        supportingContent = { Text("${transaction.date} • ${transaction.category}") },
        trailingContent = {
            val color = if (transaction.direction == HomeBankingDirection.INCOMING)
                Color(0xFF2E7D32) else MaterialTheme.colorScheme.error
            val prefix = if (transaction.direction == HomeBankingDirection.INCOMING) "+" else "-"
            Text(
                "$prefix $${transaction.amount}",
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    )
}

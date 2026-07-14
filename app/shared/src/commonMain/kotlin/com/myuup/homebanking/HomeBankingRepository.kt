package com.myuup.homebanking

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeBankingRepository {
    private val _snapshot = MutableStateFlow(getMockSnapshot())
    val snapshot: StateFlow<HomeBankingSnapshot> = _snapshot.asStateFlow()

    fun updateBalance(newBalance: Double) {
        _snapshot.value = _snapshot.value.copy(totalBalance = newBalance)
    }
}

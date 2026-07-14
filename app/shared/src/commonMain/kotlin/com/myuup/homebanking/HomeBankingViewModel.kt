package com.myuup.homebanking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HomeBankingViewModel(
    private val repository: HomeBankingRepository = HomeBankingRepository()
) : ViewModel() {
    
    val uiState: StateFlow<HomeBankingSnapshot> = repository.snapshot
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = getMockSnapshot()
        )
}

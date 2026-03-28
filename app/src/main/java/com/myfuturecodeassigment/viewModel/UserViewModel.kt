package com.myfuturecodeassigment.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myfuturecodeassigment.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel(){

    val users = repository.getUsers().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    var searchQuery = MutableStateFlow("")
    val isLoading = MutableStateFlow(true)

    val filteredUsers = searchQuery
        .flatMapLatest {
            if (it.isEmpty()) repository.getUsers()
            else repository.search(it)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            isLoading.value = true
            repository.fetchUsers()
            isLoading.value = false
        }
    }
}
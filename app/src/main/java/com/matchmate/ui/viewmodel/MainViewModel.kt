package com.matchmate.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matchmate.data.model.User
import com.matchmate.data.network.Resource
import com.matchmate.data.repository.UserListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UserListRepo) : ViewModel() {

    private val _users = MutableStateFlow<Resource<List<User>>>(Resource.Loading)
    val users: StateFlow<Resource<List<User>>> = _users

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            repository.getUsers(10).collectLatest { resource ->
                _users.value = resource
            }
        }
    }

    fun acceptUser(emailId: String) {
        viewModelScope.launch {
            repository.acceptUser(emailId)
        }
    }

    fun declineUser(emailId: String) {
        viewModelScope.launch {
            repository.declineUser(emailId)
        }
    }
}
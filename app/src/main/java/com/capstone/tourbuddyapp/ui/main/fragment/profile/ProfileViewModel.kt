package com.capstone.tourbuddyapp.ui.main.fragment.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.tourbuddyapp.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val userName: MutableLiveData<String> = MutableLiveData()
    val userEmail: MutableLiveData<String> = MutableLiveData()

    fun getUserInfo() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser!=null) {
            userName.postValue(firebaseUser.displayName)
            userEmail.postValue(firebaseUser.email)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}
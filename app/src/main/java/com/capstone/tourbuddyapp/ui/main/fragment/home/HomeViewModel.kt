package com.capstone.tourbuddyapp.ui.main.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.tourbuddyapp.data.pref.UserModel
import com.capstone.tourbuddyapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getSession():LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

//    fun logout() {
//        viewModelScope.launch {
//            userRepository.logout()
//        }
//    }
}
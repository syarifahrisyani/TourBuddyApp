package com.capstone.tourbuddyapp.ui.main.fragment.home

import androidx.lifecycle.ViewModel
import com.capstone.tourbuddyapp.data.repository.UserRepository

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getDestination() = userRepository.getDestination()

}



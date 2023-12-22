package com.capstone.tourbuddyapp.ui.detail

import androidx.lifecycle.ViewModel
import com.capstone.tourbuddyapp.data.repository.UserRepository

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getDetailDestination(name: String, description: String) = userRepository.getDetailDestination(name, description)
}
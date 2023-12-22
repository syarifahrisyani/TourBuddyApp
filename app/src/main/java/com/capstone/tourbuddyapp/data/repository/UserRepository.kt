package com.capstone.tourbuddyapp.data.repository

import androidx.lifecycle.liveData
import com.capstone.tourbuddyapp.ResultState
import com.capstone.tourbuddyapp.data.pref.UserModel
import com.capstone.tourbuddyapp.data.pref.UserPreference
import com.capstone.tourbuddyapp.model.DestinationData
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun getDestination() = liveData {
        emit(ResultState.Loading)
        try {
            val destinations = DestinationData.destination
            emit(ResultState.Success(destinations))

        } catch (e: Exception) {
            emit(ResultState.Error(e.toString()))
        }
    }

    fun getDetailDestination(name: String, description: String) = liveData {
        emit(ResultState.Loading)
        try {
            val destinations = DestinationData.destination.find { it.name == name && it.description == description}
            if (destinations != null) {
                emit(ResultState.Success(destinations))
            } else {
                emit(ResultState.Error("Destination not found"))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.toString()))
        }
    }

    companion object {
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}
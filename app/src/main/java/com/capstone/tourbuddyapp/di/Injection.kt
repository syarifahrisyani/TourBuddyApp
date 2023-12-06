package com.capstone.tourbuddyapp.di

import android.content.Context
import com.capstone.tourbuddyapp.data.pref.UserPreference
import com.capstone.tourbuddyapp.data.pref.dataStore
import com.capstone.tourbuddyapp.data.repository.UserRepository


object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}
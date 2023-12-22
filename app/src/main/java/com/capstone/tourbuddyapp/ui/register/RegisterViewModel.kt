package com.capstone.tourbuddyapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

class RegisterViewModel : ViewModel() {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val registerResult: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun register(username: String, email: String, password:String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val usernameUpdateProfile = userProfileChangeRequest {
                        displayName = username
                    }
                    val user = task.result.user
                    user!!.updateProfile(usernameUpdateProfile)
                        .addOnCompleteListener {
                            registerResult.postValue(true)
                        }
                        .addOnFailureListener { errorUser ->
                            errorMessage.postValue(errorUser.localizedMessage)
                        }
                } else {
                    errorMessage.postValue(task.exception?.localizedMessage)
                }
            }
            .addOnFailureListener { error ->
                errorMessage.postValue(error.localizedMessage)
            }
    }
}
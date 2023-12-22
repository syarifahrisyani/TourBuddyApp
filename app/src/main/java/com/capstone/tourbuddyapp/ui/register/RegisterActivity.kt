package com.capstone.tourbuddyapp.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capstone.tourbuddyapp.databinding.ActivityRegisterBinding
import com.capstone.tourbuddyapp.ui.custom.EditTextEmail
import com.capstone.tourbuddyapp.ui.custom.EditTextName
import com.capstone.tourbuddyapp.ui.custom.EditTextPassword
import com.capstone.tourbuddyapp.ui.onboarding.OnBoardActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var editTextName: EditTextName
    private lateinit var editTextEmail: EditTextEmail
    private lateinit var editTextPassword: EditTextPassword

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // inisialisasi viewmodel
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        setupAction()

        editTextName = binding.edRegisterUsername
        editTextEmail = binding.edRegisterEmail
        editTextPassword = binding.edRegisterPassword
    }

    private fun setupAction() {
        binding.btnRegis.setOnClickListener {
            val username = binding.edRegisterUsername.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            showLoading(true)

            // call function register from viewmodel
            registerViewModel.register(username, email, password)

            registerViewModel.registerResult.observe(this, { isSuccess ->
                showLoading(false)
                if (isSuccess) {
                    showToast("Registration Successful.")
                    navigateToOnBoarding()
                } else {
                    showToast("Registration failed.")
                }
            })

            registerViewModel.errorMessage.observe(this, {errorMessage ->
                showToast("Error: $errorMessage")
            })
        }
    }

    private fun navigateToOnBoarding() {
        val intent = Intent(this, OnBoardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarRegis.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
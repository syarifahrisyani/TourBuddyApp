package com.capstone.tourbuddyapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.capstone.tourbuddyapp.data.pref.UserModel
import com.capstone.tourbuddyapp.databinding.ActivityLoginBinding
import com.capstone.tourbuddyapp.helper.ViewModelFactory
import com.capstone.tourbuddyapp.ui.custom.EditTextEmail
import com.capstone.tourbuddyapp.ui.custom.EditTextPassword
import com.capstone.tourbuddyapp.ui.main.fragment.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var editTextEmail: EditTextEmail
    private lateinit var editTextPassword: EditTextPassword

    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()

        editTextEmail = binding.edLoginEmail
        editTextPassword = binding.edLoginPassword

    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            showLoading(true)

            loginViewModel.login(email, password)

            loginViewModel.loginResult.observe(this,{ isSuccess ->
                showLoading(false)
                if (isSuccess) {
                    loginViewModel.saveSession(UserModel(email, "sample_token"))
                    AlertDialog.Builder(this).apply {
                        setTitle("Success")
                        setMessage("Your success login")
                        setPositiveButton("Next") { _, _ ->
                            val intent = Intent(context, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                }
            })

            loginViewModel.errorMessage.observe(this, { errorMessage ->
                showToast("Login failed.")

            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
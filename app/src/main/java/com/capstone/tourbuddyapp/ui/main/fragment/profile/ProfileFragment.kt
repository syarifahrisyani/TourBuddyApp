package com.capstone.tourbuddyapp.ui.main.fragment.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.capstone.tourbuddyapp.databinding.FragmentProfileBinding
import com.capstone.tourbuddyapp.helper.ViewModelFactory
import com.capstone.tourbuddyapp.ui.onboarding.OnBoardActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var username: TextView
    private lateinit var email: TextView
    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireActivity().applicationContext)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // inisialisasi textview menggunakan binding
        username = binding.tvNameProfile
        email = binding.tvNameEmail

        profileViewModel.userName.observe(viewLifecycleOwner) { name ->
            username.text = name
        }

        profileViewModel.userEmail.observe(viewLifecycleOwner) { emailName ->
            email.text = emailName
        }

        profileViewModel.getUserInfo()

        // create action logout
        binding.btnLogout.setOnClickListener {
            profileViewModel.logout()

            // create intent ketika logout akan kembali ke page OnBoard
            val intent = Intent(requireContext(), OnBoardActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
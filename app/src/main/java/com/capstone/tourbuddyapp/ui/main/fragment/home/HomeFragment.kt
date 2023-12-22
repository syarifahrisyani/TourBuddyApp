package com.capstone.tourbuddyapp.ui.main.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.capstone.tourbuddyapp.ResultState
import com.capstone.tourbuddyapp.databinding.FragmentHomeBinding
import com.capstone.tourbuddyapp.helper.ViewModelFactory
import com.capstone.tourbuddyapp.model.Destination
import com.capstone.tourbuddyapp.ui.detail.DetailActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity().applicationContext)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvDestination.layoutManager = layoutManager

        val adapter = DestinationAdapter()

        homeViewModel.getDestination().observe(viewLifecycleOwner) { destinationList ->

            adapter.setOnItemClickCallback(object : DestinationAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Destination) {
                    navigateToDetailActivity(data)
                }
            })

            if (destinationList != null) {
                when (destinationList) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        showLoading(false)
                        adapter.submitList(destinationList.data)
                        binding.rvDestination.adapter = adapter
                    }

                    is ResultState.Error -> {
                        showToast(destinationList.error)
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun navigateToDetailActivity(data: Destination) {
        Intent(requireContext(), DetailActivity::class.java).also {
            it.putExtra(DetailActivity.NAME_DESTINATION, data)
            startActivity(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarHome.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}
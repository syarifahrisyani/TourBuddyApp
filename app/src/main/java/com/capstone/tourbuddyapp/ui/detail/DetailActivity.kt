package com.capstone.tourbuddyapp.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.capstone.tourbuddyapp.ResultState
import com.capstone.tourbuddyapp.databinding.ActivityDetailBinding
import com.capstone.tourbuddyapp.helper.ViewModelFactory
import com.capstone.tourbuddyapp.model.Destination

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // create move layout detail destination
        val destination = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Destination>(NAME_DESTINATION, Destination::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Destination>(NAME_DESTINATION)
        }
        getDetailDestination(destination)

    }

    private fun getDetailDestination(destination: Destination?) {
        destination?.let {
            detailViewModel.getDetailDestination(it.name, it.description)
                .observe(this) { detailDestination ->
                    when (detailDestination) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }
                        is ResultState.Success -> {
                            showLoading(false)
                            binding.tvDetailDestination.text = detailDestination.data.name
                            binding.tvDetailLocation.text = detailDestination.data.locationName
                            binding.tvDetailDescription.text = detailDestination.data.description
                            Glide.with(binding.root.context)
                                .load(detailDestination.data.photo)
                                .into(binding.ivDetailPhoto)
                        }
                        is ResultState.Error -> {
                            showToast(detailDestination.error)
                            showLoading(false)
                        }
                    }
                }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val NAME_DESTINATION = "name_destination"
    }
}
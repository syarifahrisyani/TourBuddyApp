package com.capstone.tourbuddyapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Destination(
    val name: String,
    val locationName: String,
    val description: String,
    val photo: Int
) : Parcelable
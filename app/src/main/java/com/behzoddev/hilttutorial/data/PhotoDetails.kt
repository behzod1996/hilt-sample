package com.behzoddev.hilttutorial.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoDetails(
    val url: String?,
    val description: String?,
    val votesCount: Int?,
    val comments: Int?,
    val pulse: Double?,
    val impressions: Int?,
    val photoTitle: String?,
    val userName: String?,
    val userPhotoUrl: String?,
    val exif: String?,
    val durationPosted: String?
) : Parcelable
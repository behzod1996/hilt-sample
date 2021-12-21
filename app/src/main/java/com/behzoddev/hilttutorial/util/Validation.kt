package com.behzoddev.hilttutorial.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.behzoddev.hilttutorial.data.Photo
import java.time.Duration
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun Photo.getValidation() = java.lang.StringBuilder().apply {
    append(if (camera != null && camera.isBlank()) "Unknown camera" else camera)
    append("+")
    append(if (lens.isNullOrBlank()) "Unknown lens" else lens)
    append(" | ")
    append(if (focalLength.isNullOrBlank()) "Unknown focal length" else focalLength + "mm")
    appendLine()
    append(if (aperture != null && aperture.isBlank()) "f0" else "f/$aperture")
    append(" | ")
    append(if (shutterSpeed.isNullOrBlank()) "0s" else shutterSpeed + "s")
    append(" | ")
    append(if (iso.isNullOrBlank()) "ISO" else "ISO = $iso")
}.run {
    toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Photo.durationPosted(): String {
    val timeCreatedDate =
        OffsetDateTime.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME).toLocalDateTime()
    val duration = Duration.between(timeCreatedDate, LocalDateTime.now())
    return when {
        duration.toDays() == 1L -> {
            "${duration.toDays()} year"
        }
        duration.toDays() > 1 -> {
            "${duration.toDays()} year"
        }
        duration.toHours() == 1L -> {
            "${duration.toHours()} hours"
        }
        duration.toHours() > 1 -> {
            "${duration.toHours()} hours"
        }
        duration.toMinutes() == 1L -> {
            "${duration.toMinutes()} minutes"
        }
        duration.toMinutes() > 1 -> {
            "${duration.toMinutes()} minutes"
        }
        else -> {
            "Less than a minute"
        }
    }.run {
        "$this ago"
    }
}
package com.sambernal.assignment.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class School(
    @SerializedName("dbn")
    val id: String,
    @SerializedName("school_name")
    val name: String,
    @SerializedName("overview_paragraph")
    val overview: String,
    @SerializedName("language_classes")
    val languageClasses: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("website")
    val website: String,
    @SerializedName("school_email")
    val email: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("phone_number")
    val phoneNumber: String
) : Parcelable

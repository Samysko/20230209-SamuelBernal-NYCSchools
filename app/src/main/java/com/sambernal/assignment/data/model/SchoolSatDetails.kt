package com.sambernal.assignment.data.model

import com.google.gson.annotations.SerializedName

data class SchoolSatDetails(
    @SerializedName("dbn")
    val id: String,
    @SerializedName("school_name")
    val schoolName: String,
    @SerializedName("num_of_sat_test_takers")
    val numberOfSatTestTakers: String,
    @SerializedName("sat_critical_reading_avg_score")
    val satCriticalReadingAverageScore: String,
    @SerializedName("sat_writing_avg_score")
    val satWritingAverageScore: String,
    @SerializedName("sat_math_avg_score")
    val satMathAverageScore: String
)

package com.sambernal.assignment.api

import com.sambernal.assignment.data.model.School
import com.sambernal.assignment.data.model.SchoolSatDetails
import retrofit2.http.GET

interface SchoolsApiService {
    @GET("resource/s3k6-pzi2.json")
    suspend fun getSchools(): List<School>

    @GET("resource/f9bf-2cp4.json")
    suspend fun getSchoolsSatDetails(): List<SchoolSatDetails>
}
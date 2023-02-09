package com.sambernal.assignment.data

import com.sambernal.assignment.api.SchoolsApiService
import com.sambernal.assignment.data.model.School
import com.sambernal.assignment.data.model.SchoolSatDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SchoolsRepository(private val apiService: SchoolsApiService) {
    suspend fun getSchools(): Flow<List<School>> = flow {
        emit(apiService.getSchools())
    }

    suspend fun getSchoolSATDetails(schoolId: String): Flow<SchoolSatDetails?> = flow {
        emit(apiService.getSchoolsSatDetails().find { listOfSchoolSatDetails ->
            listOfSchoolSatDetails.id == schoolId
        })
    }

}
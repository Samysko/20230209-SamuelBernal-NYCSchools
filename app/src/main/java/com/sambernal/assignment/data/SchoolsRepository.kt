package com.sambernal.assignment.data

import com.sambernal.assignment.api.SchoolsApiService
import com.sambernal.assignment.data.model.School
import com.sambernal.assignment.data.model.SchoolSatDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SchoolsRepository @Inject constructor(
    private val apiService: SchoolsApiService,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun getSchools(): Flow<List<School>> = flow {
        emit(apiService.getSchools())
    }.flowOn(dispatcher)

    suspend fun getSchoolSATDetails(schoolId: String): Flow<SchoolSatDetails?> = flow {
        emit(apiService.getSchoolsSatDetails().find { listOfSchoolSatDetails ->
            listOfSchoolSatDetails.id == schoolId
        })
    }.flowOn(dispatcher)

}
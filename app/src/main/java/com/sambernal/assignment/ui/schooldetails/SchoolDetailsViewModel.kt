package com.sambernal.assignment.ui.schooldetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sambernal.assignment.data.SchoolsRepository
import com.sambernal.assignment.data.model.SchoolSatDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SchoolSatDetailsViewModel @Inject constructor(
    private val schoolsRepository: SchoolsRepository
) : ViewModel() {
    @OptIn(FlowPreview::class)
    suspend fun getSchoolSatDetails(schoolId: String): Flow<SchoolSatDetailsUIState> =
        schoolsRepository
            .getSchoolSATDetails(schoolId).map { schoolSatDetails ->
                schoolSatDetails?.let {
                    SchoolSatDetailsUIState.Success(it)
                } ?: SchoolSatDetailsUIState.Error(Throwable("Sorry, school SAT details not found"))
            }.debounce(5000)
            .catch { SchoolSatDetailsUIState.Error(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                SchoolSatDetailsUIState.Loading
            )
}

sealed interface SchoolSatDetailsUIState {
    object Loading : SchoolSatDetailsUIState
    data class Error(val throwable: Throwable) : SchoolSatDetailsUIState
    data class Success(val schoolSatDetails: SchoolSatDetails) : SchoolSatDetailsUIState
}
package com.sambernal.assignment.ui.listofschools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sambernal.assignment.data.SchoolsRepository
import com.sambernal.assignment.data.model.School
import com.sambernal.assignment.ui.listofschools.SchoolsUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SchoolsViewModel @Inject constructor(
    private val schoolsRepository: SchoolsRepository
) : ViewModel() {

    suspend fun getSchools(): StateFlow<SchoolsUiState> = schoolsRepository
        .getSchools().map(::Success)
        .catch { SchoolsUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SchoolsUiState.Loading)
}

sealed interface SchoolsUiState {
    object Loading : SchoolsUiState
    data class Error(val throwable: Throwable) : SchoolsUiState
    data class Success(val listOfSchools: List<School>) : SchoolsUiState
}
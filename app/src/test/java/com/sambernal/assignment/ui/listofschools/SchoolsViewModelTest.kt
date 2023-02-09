package com.sambernal.assignment.ui.listofschools

import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.sambernal.assignment.data.SchoolsRepository
import kotlinx.coroutines.flow.*
import org.junit.Before
import org.mockito.Mock

@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class SchoolsViewModelTest {
    private lateinit var viewModel: SchoolsViewModel

    @Mock
    private lateinit var schoolsRepository: SchoolsRepository

    @Before
    fun setUp() {
        viewModel = SchoolsViewModel(schoolsRepository)
    }
}

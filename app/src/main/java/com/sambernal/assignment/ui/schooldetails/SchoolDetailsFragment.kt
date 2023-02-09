package com.sambernal.assignment.ui.schooldetails

import android.os.Build.VERSION
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sambernal.assigment.databinding.FragmentSchoolSatDetailsBinding
import com.sambernal.assignment.data.model.School
import com.sambernal.assignment.data.model.SchoolSatDetails
import com.sambernal.assignment.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SchoolDetailsFragment : Fragment() {
    private val viewModel: SchoolSatDetailsViewModel by viewModels()

    private var _binding: FragmentSchoolSatDetailsBinding? = null
    private val binding get() = _binding!!

    private var school: School? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        school = if (VERSION.SDK_INT >= 33) {
            requireArguments().getParcelable(SCHOOL_ARG, School::class.java)
        } else {
            requireArguments().getParcelable(SCHOOL_ARG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchoolSatDetailsBinding.inflate(inflater, container, false)

        binding.apply {
            school?.let { school ->
                textViewSchoolNameTitle.text = school.name

                setUpGeneralDetailsSection(school)
                setUpContactDetailsSection(school)

                lifecycleScope.launch {
                    fetchAndSetUpSchoolSatDetails(school.id)
                }
            }
        }

        return binding.root
    }

    private fun setUpGeneralDetailsSection(school: School) {
        with(binding) {
            textViewSchoolOverviewValue.text = school.overview
            textViewLanguageClassesValue.text = school.languageClasses
            textViewLocationValue.text = school.location
        }
    }

    private fun setUpContactDetailsSection(school: School) {
        with(binding) {
            textViewPhoneNumberValue.text = school.phoneNumber
            textViewWebsiteValue.text = school.website
            textViewEmailValue.text = school.email
        }
    }

    private suspend fun fetchAndSetUpSchoolSatDetails(schoolId: String) {
        viewModel.getSchoolSatDetails(schoolId).collect { schoolSatDetailsUIState ->
            when (schoolSatDetailsUIState) {
                is SchoolSatDetailsUIState.Error -> {
                    Toast.makeText(
                        context,
                        schoolSatDetailsUIState.throwable.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is SchoolSatDetailsUIState.Loading -> {
                    // Do nothing
                }
                is SchoolSatDetailsUIState.Success -> {
                    setUpSatDetailsSection(schoolSatDetailsUIState.schoolSatDetails)
                }
            }

        }
    }

    private fun setUpSatDetailsSection(schoolSatDetails: SchoolSatDetails) {
        with(binding) {
            textViewSatDetailsSubtitle.visible()
            textViewMathAvgScore.visible()
            textViewMathAvgScoreValue.visible()
            textViewMathAvgScoreValue.text = schoolSatDetails.satMathAverageScore
            textViewNumberOfTestTakers.visible()
            textViewNumberOfTestTakersValue.visible()
            textViewNumberOfTestTakersValue.text = schoolSatDetails.numberOfSatTestTakers
            textViewCriticalReadingAvgScore.visible()
            textViewCriticalReadingAvgScoreValue.visible()
            textViewCriticalReadingAvgScoreValue.text =
                schoolSatDetails.satCriticalReadingAverageScore
            textViewWritingAvgScore.visible()
            textViewTextViewWritingAvgScoreValue.visible()
            textViewTextViewWritingAvgScoreValue.text = schoolSatDetails.satWritingAverageScore
        }
    }

    companion object {
        private const val SCHOOL_ARG = "school"

        fun newInstance(school: School): SchoolDetailsFragment {
            val fragment = SchoolDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(SCHOOL_ARG, school)
                }
            }
            return fragment
        }
    }
}
package com.sambernal.assignment.ui.listofschools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sambernal.assigment.R
import com.sambernal.assigment.databinding.FragmentSchoolsBinding
import com.sambernal.assignment.ui.listofschools.recycler.SchoolsAdapter
import com.sambernal.assignment.ui.schooldetails.SchoolDetailsFragment
import com.sambernal.assignment.util.gone
import com.sambernal.assignment.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SchoolsFragment : Fragment() {

    private val viewModel: SchoolsViewModel by viewModels()

    private var _binding: FragmentSchoolsBinding? = null
    private val binding get() = _binding!!

    private var _schoolsAdapter: SchoolsAdapter? = null
    private val schoolsAdapter get() = _schoolsAdapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchoolsBinding.inflate(inflater, container, false)

        binding.apply {
            lifecycleScope.launch {
                viewModel.getSchools().collect { schoolState ->
                    when (schoolState) {
                        is SchoolsUiState.Error -> {
                            Toast.makeText(
                                context,
                                schoolState.throwable.localizedMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        is SchoolsUiState.Loading -> {
                            binding.progressBarLoading.visible()
                        }
                        is SchoolsUiState.Success -> {
                            binding.progressBarLoading.gone()
                            _schoolsAdapter = SchoolsAdapter(schoolState.listOfSchools) {
                                parentFragmentManager.beginTransaction()
                                    .addToBackStack(null)
                                    .replace(
                                        R.id.fragment_container,
                                        SchoolDetailsFragment.newInstance(it)
                                    )
                                    .commit()
                            }
                            binding.recyclerViewSchools.adapter = schoolsAdapter
                        }
                    }
                }
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        _schoolsAdapter = null
        super.onDestroy()
    }

    companion object {
        fun newInstance(): SchoolsFragment {
            return SchoolsFragment()
        }
    }

}
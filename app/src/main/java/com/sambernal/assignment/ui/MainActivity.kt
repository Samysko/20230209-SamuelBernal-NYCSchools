package com.sambernal.assignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sambernal.assigment.R
import com.sambernal.assignment.ui.listofschools.SchoolsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .add(R.id.fragment_container, SchoolsFragment.newInstance())
                .commit()
        }
    }
}

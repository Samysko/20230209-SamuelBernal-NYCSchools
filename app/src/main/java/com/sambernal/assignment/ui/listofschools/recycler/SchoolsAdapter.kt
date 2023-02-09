package com.sambernal.assignment.ui.listofschools.recycler

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.sambernal.assigment.R
import com.sambernal.assigment.databinding.ItemSchoolBinding
import com.sambernal.assignment.data.model.School

class SchoolsAdapter(private val listOfSchools: List<School>, private val clickListener: (School) -> Unit) :
    RecyclerView.Adapter<SchoolsAdapter.SchoolsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolsViewHolder {
        return SchoolsViewHolder(ItemSchoolBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SchoolsViewHolder, position: Int) {
        holder.bind(listOfSchools[position], clickListener)
    }

    override fun getItemCount(): Int {
        return listOfSchools.size
    }

    inner class SchoolsViewHolder(private val binding: ItemSchoolBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(school: School, clickListener: (School) -> Unit) {
            with(binding) {
                textViewSchoolName.text = school.name
                textViewCity.text = school.city

                if (school.phoneNumber.isNotBlank()) {
                    phoneButtonLogicSetUp(school.phoneNumber)
                } else {
                    imageButtonPhone.visibility = View.INVISIBLE
                }

                binding.root.setOnClickListener {
                    clickListener(school)
                }
            }
        }

        fun phoneButtonLogicSetUp(phoneNumber: String) {
            binding.imageButtonPhone.setOnClickListener {
                val callIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${phoneNumber}")
                }

                try {
                    startActivity(binding.root.context, callIntent, null)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        binding.root.context,
                        binding.root.context.getString(R.string.could_not_dial_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
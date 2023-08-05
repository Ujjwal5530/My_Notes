@file:Suppress("DEPRECATION")

package com.malhotra.mynotes.fragments

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.malhotra.mynotes.R
import com.malhotra.mynotes.databinding.FragmentCreateNotesBinding
import com.malhotra.mynotes.model.Notes
import com.malhotra.mynotes.viewModel.ViewModel
import java.util.*

class CreateNotesFragment : Fragment() {

    private var _binding : FragmentCreateNotesBinding?= null
    private val binding get() = _binding!!
    var priority : String = "1"
    val viewModel : ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateNotesBinding.inflate(inflater, container, false)

        binding.pGreen.setImageResource(R.drawable.ic_baseline_done_24)

        binding.pGreen.setOnClickListener {
            priority = "1"
            binding.pGreen.setImageResource(R.drawable.ic_baseline_done_24)
            binding.pYellow.setImageResource(0)
            binding.pOrange.setImageResource(0)
        }

        binding.pYellow.setOnClickListener {
            priority = "2"
            binding.pYellow.setImageResource(R.drawable.ic_baseline_done_24)
            binding.pGreen.setImageResource(0)
            binding.pOrange.setImageResource(0)
        }

        binding.pOrange.setOnClickListener {
            priority = "3"
            binding.pOrange.setImageResource(R.drawable.ic_baseline_done_24)
            binding.pGreen.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }

        binding.btnSave.setOnClickListener {
            if (binding.editTitle.text.isEmpty()) {
                binding.editTitle.error = "Title can not be empty"
                return@setOnClickListener
            } else createNotes(it)
        }


        return binding.root
    }

    private fun createNotes(it: View?) {
        val title = binding.editTitle.text.toString()
        val notes = binding.editNotes.text.toString()
        val d = Date()
        val date = DateFormat.format("MMMM d, yyyy ", d.time)
        val notesData = Notes(null, title, notes, date.toString(), priority)
        viewModel.insertNotes(notesData)

        Navigation.findNavController(it!!)
            .navigate(R.id.action_createNotesFragment_to_homeFragment)

        Toast.makeText(requireContext(), "A Note of $title is created", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
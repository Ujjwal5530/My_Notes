@file:Suppress("DEPRECATION")

package com.malhotra.mynotes.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.malhotra.mynotes.R
import com.malhotra.mynotes.databinding.FragmentEditNotesBinding
import com.malhotra.mynotes.model.Notes
import com.malhotra.mynotes.viewModel.ViewModel
import java.util.*

class EditNotesFragment : Fragment() {

    val notes by navArgs<EditNotesFragmentArgs>()
    private lateinit var priority : String
    private var _binding : FragmentEditNotesBinding? = null
    private val binding get() = _binding!!
    val viewModel : ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditNotesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        binding.editTitle.setText(notes.notes.title)
        binding.editNotes.setText(notes.notes.notes)

        when(notes.notes.priority){
            "1" -> {
                priority = "1"
                binding.pGreen.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pYellow.setImageResource(0)
                binding.pOrange.setImageResource(0)
            }
            "2" -> {
                priority = "2"
                binding.pYellow.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pGreen.setImageResource(0)
                binding.pOrange.setImageResource(0)
            }
            "3" -> {
                priority = "3"
                binding.pOrange.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pGreen.setImageResource(0)
                binding.pYellow.setImageResource(0)
            }
        }

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

        binding.btnEditSave.setOnClickListener {
            if (binding.editTitle.text.isEmpty()) {
                binding.editTitle.error = "Title can not be empty"
                return@setOnClickListener
            } else updateNotes(it)
        }

        return binding.root
    }

    private fun updateNotes(it: View?) {
        val title = binding.editTitle.text.toString()
        val note = binding.editNotes.text.toString()
        val d = Date()
        val date = DateFormat.format("MMMM d, yyyy ", d.time)
        val notesData = Notes(notes.notes.id, title, note, date.toString(), priority)
        viewModel.updateNotes(notesData)

        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment)
        Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.delete_icon) {

            val bottomSheet = BottomSheetDialog(requireContext())
            bottomSheet.setContentView(R.layout.dialog_delete)

            val yes= bottomSheet.findViewById<TextView>(R.id.yes)
            val no = bottomSheet.findViewById<TextView>(R.id.no)

            yes?.setOnClickListener {
                viewModel.deleteNotes(notes.notes.id!!)
                /*A dialog fragment works in a different window,
                and does not comes under the same view hierarchy of our defined navController.*/
                val action = EditNotesFragmentDirections.actionEditNotesFragmentToHomeFragment()
                val navHostFragment = requireActivity()
                    .supportFragmentManager
                    .findFragmentById(R.id.fragmentContainerView) as NavHostFragment

                navHostFragment.navController.navigate(action)
                bottomSheet.dismiss()
            }

            no?.setOnClickListener {
                bottomSheet.dismiss()
            }
            bottomSheet.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
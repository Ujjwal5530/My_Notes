@file:Suppress("DEPRECATION")

package com.malhotra.mynotes.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.malhotra.mynotes.R
import com.malhotra.mynotes.adapters.NotesAdapter
import com.malhotra.mynotes.databinding.FragmentHomeBinding
import com.malhotra.mynotes.model.Notes
import com.malhotra.mynotes.viewModel.ViewModel

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding?= null
    private val binding get() = _binding!!
    val viewModel : ViewModel by viewModels()
    private lateinit var oldNotes : ArrayList<Notes>
    lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        binding.btnAdd.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
            oldNotes = notesList as ArrayList<Notes>
            binding.allNotesRv.adapter = null
            adapter = NotesAdapter(requireContext(), notesList)
            binding.allNotesRv.layoutManager = StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL)
            binding.allNotesRv.adapter = adapter
        }

        binding.highBtn.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner) { notesList ->
                oldNotes = notesList as ArrayList<Notes>
                binding.allNotesRv.adapter = null
                adapter = NotesAdapter(requireContext(), notesList)
                binding.allNotesRv.layoutManager = StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL)
                binding.allNotesRv.adapter = adapter
            }
        }

        binding.mediumBtn.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner) { notesList ->
                oldNotes = notesList as ArrayList<Notes>
                binding.allNotesRv.adapter = null
                adapter = NotesAdapter(requireContext(), notesList)
                binding.allNotesRv.layoutManager = StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL)
                binding.allNotesRv.adapter = adapter
            }
        }

        binding.lowBtn.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner) { notesList ->
                oldNotes = notesList as ArrayList<Notes>
                binding.allNotesRv.adapter = null
                adapter = NotesAdapter(requireContext(), notesList)
                binding.allNotesRv.layoutManager = StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL)
                binding.allNotesRv.adapter = adapter
            }
        }

        binding.allNotes.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
                oldNotes = notesList as ArrayList<Notes>
                binding.allNotesRv.adapter = null
                adapter = NotesAdapter(requireContext(), notesList)
                binding.allNotesRv.layoutManager = StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL)
                binding.allNotesRv.adapter = adapter
            }
        }

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val item = menu.findItem(R.id.bar_search)
        val searchView : SearchView = item.actionView as SearchView
        searchView.queryHint = "Enter Notes Here..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newFilteredList(newText)
                return true
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun newFilteredList(newText: String?) {

        val newList = arrayListOf<Notes>()

        for (i in oldNotes){
            if (i.title.toString().lowercase().contains(newText!!.lowercase())
                || i.subTitle.toString().lowercase().contains(newText.lowercase())){
                newList.add(i)
            }
        }
        adapter.filtering(newList)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
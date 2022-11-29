package com.malhotra.mynotes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.malhotra.mynotes.R
import com.malhotra.mynotes.databinding.ItemNotesBinding
import com.malhotra.mynotes.fragments.HomeFragmentDirections
import com.malhotra.mynotes.model.Notes

class NotesAdapter(private val context: Context, private var notesList : List<Notes>)
    : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>(){

    fun filtering(newList : ArrayList<Notes>){
        notesList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(LayoutInflater
            .from(context)
            .inflate(R.layout.item_notes, parent, false))
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val notes = notesList[position]

        holder.binding.title.text = notes.title
        holder.binding.subTitle.text = notes.subTitle
        holder.binding.date.text = notes.date

        when(notes.priority){
            "1" -> holder.binding.viewPriority.setBackgroundResource(R.drawable.green_dot)
            "2" -> holder.binding.viewPriority.setBackgroundResource(R.drawable.yellow_dot)
            "3" -> holder.binding.viewPriority.setBackgroundResource(R.drawable.orange_dot)
        }

        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(notes)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    class NotesViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val binding = ItemNotesBinding.bind(view)
    }
}
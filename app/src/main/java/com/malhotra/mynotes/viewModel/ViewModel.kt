package com.malhotra.mynotes.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.malhotra.mynotes.dao.NotesDao
import com.malhotra.mynotes.database.NotesDatabase
import com.malhotra.mynotes.model.Notes
import com.malhotra.mynotes.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotesRepository

    init {
        val dao = NotesDatabase.getDatabaseInstance(application).notesDao()
        repository = NotesRepository(dao)
    }

    fun getNotes() : LiveData<List<Notes>> {
        return repository.getNotes()
    }

    fun insertNotes(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNotes(notes)
        }
    }

    fun deleteNotes(id : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNotes(id)
        }
    }

    fun updateNotes(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNotes(notes)
        }
    }

    fun getHighNotes() : LiveData<List<Notes>>{
        return repository.getHighNotes()
    }

    fun getMediumNotes() : LiveData<List<Notes>>{
        return repository.getMediumNotes()
    }

    fun getLowNotes() : LiveData<List<Notes>>{
        return repository.getLowNotes()
    }

}
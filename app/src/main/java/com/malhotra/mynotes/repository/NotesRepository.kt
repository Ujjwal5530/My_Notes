package com.malhotra.mynotes.repository

import androidx.lifecycle.LiveData
import com.malhotra.mynotes.dao.NotesDao
import com.malhotra.mynotes.model.Notes

class NotesRepository(private val dao : NotesDao) {

    fun getNotes() : LiveData<List<Notes>>{
        return dao.getNotes()
    }

    fun getHighNotes() : LiveData<List<Notes>>{
        return dao.getHighNotes()
    }

    fun getMediumNotes() : LiveData<List<Notes>>{
        return dao.getMediumNotes()
    }

    fun getLowNotes() : LiveData<List<Notes>>{
        return dao.getLowNotes()
    }

    suspend fun insertNotes(notes: Notes) {
        dao.insertNotes(notes)
    }

    suspend fun deleteNotes(id : Int) {
        dao.deleteNotes(id)
    }

    suspend fun updateNotes(notes: Notes) {
        dao.updateNotes(notes)
    }

}
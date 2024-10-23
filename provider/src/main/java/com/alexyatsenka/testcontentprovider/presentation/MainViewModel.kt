package com.alexyatsenka.testcontentprovider.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyatsenka.models.domain.Note
import com.alexyatsenka.testcontentprovider.domain.usecase.addNote.AddNewNoteUseCase
import com.alexyatsenka.testcontentprovider.domain.usecase.deleteNotes.DeleteNotesUseCase
import com.alexyatsenka.testcontentprovider.domain.usecase.getNotes.GetNotesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    getNotesUseCase: GetNotesUseCase,
    private val addNewNoteUseCase: AddNewNoteUseCase,
    private val deleteNotesUseCase: DeleteNotesUseCase
) : ViewModel() {

    private var mExpand by mutableStateOf(false)
    private var mShowDelete by mutableStateOf(false)
    private val mListToDelete = mutableStateListOf<Note>()

    var currentTitle by mutableStateOf("")
    var currentContent by mutableStateOf("")

    val expand get() = mExpand
    val showDelete get() = mShowDelete
    val notes = getNotesUseCase().getOrThrow()
    val listToDelete get() = mListToDelete.toList()


    fun addNewNote() {
        viewModelScope.launch {
            mExpand = false
            addNewNoteUseCase(
                Note(
                    title = currentTitle,
                    content = currentContent
                )
            )
            currentTitle = ""
            currentContent = ""
        }
    }

    fun deleteNotes() {
        viewModelScope.launch {
            mShowDelete = false
            deleteNotesUseCase(mListToDelete)
            mListToDelete.clear()
        }
    }

    fun clickToAddCard() { mExpand = !mExpand }
    fun showDelete() {
        mShowDelete = !mShowDelete
        if(!mShowDelete) { mListToDelete.clear() }
    }
    fun clickToDelete(note : Note) {
        if(!mListToDelete.remove(note))
            mListToDelete.add(note)
    }
}
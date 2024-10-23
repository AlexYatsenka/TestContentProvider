package com.alexyatsenka.userprovider.presentation.main

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
import com.alexyatsenka.userprovider.domain.usecase.updateNote.UpdateNoteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    getNotesUseCase: GetNotesUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val addNewNoteUseCase: AddNewNoteUseCase,
    private val deleteNotesUseCase: DeleteNotesUseCase
) : ViewModel() {

    private var mExpand by mutableStateOf(false)
    private var mShowDelete by mutableStateOf(false)
    private val mSelectedItems = mutableStateListOf<Note>()
    private var mEditedItem by mutableStateOf<Note?>(null)

    var currentTitle by mutableStateOf("")
    var currentContent by mutableStateOf("")

    val expand get() = mExpand
    val showDelete get() = mShowDelete
    val editedItem get() = mEditedItem
    val notes = getNotesUseCase().getOrThrow()
    val selectedItems get() = mSelectedItems.toList()


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
            deleteNotesUseCase(mSelectedItems)
            mSelectedItems.clear()
        }
    }

    fun clickToAddCard() { mExpand = !mExpand }
    fun showDelete() {
        mShowDelete = !mShowDelete
        if(!mShowDelete) { mSelectedItems.clear() }
    }
    fun clickToDelete(note : Note) {
        if(!mSelectedItems.remove(note))
            mSelectedItems.add(note)
    }
    fun clickToEdit() {
        mEditedItem = selectedItems.first()
        currentTitle = editedItem!!.title
        currentContent = editedItem!!.content
        mShowDelete = false
    }
    fun cancelEdit() {
        currentTitle = ""
        currentContent = ""
        mShowDelete = false
        mEditedItem = null
    }
    fun saveEditItem() {
        viewModelScope.launch {
            updateNoteUseCase(
                editedItem!!.copy(
                    title = currentTitle,
                    content = currentContent
                )
            )
            cancelEdit()
        }
    }
}
package com.alexyatsenka.userprovider.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyatsenka.models.domain.Note
import com.alexyatsenka.testcontentprovider.domain.usecase.addNote.AddNewNoteUseCase
import com.alexyatsenka.userprovider.domain.usecase.deleteNotes.DeleteNotesUseCase
import com.alexyatsenka.userprovider.domain.usecase.getNotes.GetNotesUseCase
import com.alexyatsenka.userprovider.domain.usecase.updateNote.UpdateNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    getNotesUseCase: GetNotesUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val addNewNoteUseCase: AddNewNoteUseCase,
    private val deleteNotesUseCase: DeleteNotesUseCase
) : ViewModel() {

    private val mExpand = MutableStateFlow(false)
    private val mShowDelete = MutableStateFlow(false)
    private val mSelectedItems = MutableStateFlow<List<Note>>(emptyList())
    private val mEditedItem = MutableStateFlow<Note?>(null)

    val currentTitle = MutableStateFlow("")
    val currentContent = MutableStateFlow("")

    val expand = mExpand.asStateFlow()
    val showDelete = mShowDelete.asStateFlow()
    val editedItem = mEditedItem.asStateFlow()
    val notes = getNotesUseCase().getOrThrow()
    val selectedItems = mSelectedItems.asStateFlow()

    fun setTitle(title : String) {
        currentTitle.update { title }
    }
    fun setContent(content : String) {
        currentContent.update { content }
    }

    fun addNewNote() {
        viewModelScope.launch {
            mExpand.update { false }
            addNewNoteUseCase(
                Note(
                    title = currentTitle.value,
                    content = currentContent.value
                )
            )
            currentContent.update { "" }
            currentTitle.update { "" }
        }
    }
    fun deleteNotes() {
        mShowDelete.update { false }
        deleteNotesUseCase(mSelectedItems.value)
        mSelectedItems.update { emptyList() }
    }
    fun clickToAddCard() { mExpand.update { !it } }
    fun showDelete() {
        mShowDelete.update { !it }
        if(!mShowDelete.value) { mSelectedItems.update { emptyList() } }
    }
    fun clickToDelete(note : Note) {
        mSelectedItems.update {
            it.toMutableList().also {
                if(!it.remove(note)) it.add(note)
            }
        }
    }
    fun clickToEdit() {
        selectedItems.value.first().also { selectedItem ->
            mEditedItem.update { selectedItem }
            currentTitle.update { selectedItem.title }
            currentContent.update { selectedItem.content }
            mShowDelete.update { false }
        }
    }
    fun cancelEdit() {
        currentTitle.update { "" }
        currentContent.update { "" }
        mShowDelete.update { false }
        mEditedItem.update { null }
    }
    fun saveEditItem() {
        editedItem.value?.let {
            updateNoteUseCase(
                it.copy(
                    title = currentTitle.value,
                    content = currentContent.value
                )
            )
        }
        cancelEdit()
    }
}
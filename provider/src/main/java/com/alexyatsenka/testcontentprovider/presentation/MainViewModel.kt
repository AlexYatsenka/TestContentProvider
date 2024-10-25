package com.alexyatsenka.testcontentprovider.presentation

import androidx.lifecycle.ViewModel
import com.alexyatsenka.models.domain.Note
import com.alexyatsenka.testcontentprovider.domain.usecase.getNotes.GetNotesUseCase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainViewModel @Inject constructor(
    getNotesUseCase: GetNotesUseCase
) : ViewModel() {
    val notes = getNotesUseCase().let {
        if(it.isSuccess) it.getOrThrow()
        else flow { emptyList<Note>() }
    }
}
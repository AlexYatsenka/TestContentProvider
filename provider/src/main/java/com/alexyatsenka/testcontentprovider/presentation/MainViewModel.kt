package com.alexyatsenka.testcontentprovider.presentation

import androidx.lifecycle.ViewModel
import com.alexyatsenka.testcontentprovider.domain.usecase.getNotes.GetNotesUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    getNotesUseCase: GetNotesUseCase
) : ViewModel() {
    val notes = getNotesUseCase().getOrThrow()
}
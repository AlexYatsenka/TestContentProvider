package com.alexyatsenka.userprovider.presentation.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexyatsenka.userprovider.R
import com.alexyatsenka.userprovider.presentation.main.MainViewModel

@Composable
fun ExpandAddButton(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = viewModel.currentTitle,
            onValueChange = { viewModel.currentTitle = it },
            label = { Text(text = stringResource(R.string.main_note_title)) },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = viewModel.currentContent,
            onValueChange = { viewModel.currentContent = it },
            label = { Text(text = stringResource(R.string.main_note_content)) },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { viewModel.addNewNote() }
        ) { Text(text = stringResource(R.string.main_add_note)) }
    }
}
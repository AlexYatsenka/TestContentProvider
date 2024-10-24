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

@Composable
fun ExpandAddButton(
    title : String,
    content : String,
    onTitleUpdate : (String) -> Unit,
    onContentUpdate : (String) -> Unit,
    onAddNote : () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = title,
            onValueChange = onTitleUpdate,
            label = { Text(text = stringResource(R.string.main_note_title)) },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = content,
            onValueChange = onContentUpdate,
            label = { Text(text = stringResource(R.string.main_note_content)) },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onAddNote
        ) { Text(text = stringResource(R.string.main_add_note)) }
    }
}
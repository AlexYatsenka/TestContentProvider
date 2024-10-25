package com.alexyatsenka.userprovider.presentation.main.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AddButton(
    title : String,
    content : String,
    expand : Boolean,
    onTitleUpdate : (String) -> Unit,
    onContentUpdate : (String) -> Unit,
    onAddNote : () -> Unit,
    onClick : () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .animateContentSize()
            .clickable(onClick = onClick)
    ) {
        Crossfade(
            targetState = expand,
            label = ""
        ) {
            if(it) {
                ExpandAddButton(
                    title = title,
                    content = content,
                    onTitleUpdate = onTitleUpdate,
                    onContentUpdate = onContentUpdate,
                    onAddNote = onAddNote
                )
            } else {
                CollapsedAddButton()
            }
        }
    }
}
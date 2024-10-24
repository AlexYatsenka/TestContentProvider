package com.alexyatsenka.userprovider.presentation.main.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexyatsenka.models.domain.Note

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Item(
    item : Note,
    showEdit : Boolean,
    checked : Boolean,
    title : String,
    content : String,
    showDelete : Boolean,
    onClickToDelete : () -> Unit,
    onShowDelete : () -> Unit,
    onClickCancel : () -> Unit,
    onClickSave : () -> Unit,
    onTitleUpdate : (String) -> Unit,
    onContentUpdate : (String) -> Unit,
    modifier : Modifier = Modifier
) {
    Card(
        modifier = modifier
            .animateContentSize()
            .combinedClickable(
                onClick = onClickToDelete,
                onLongClick = onShowDelete
            )
    ) {
        Crossfade(
            targetState = showEdit,
            label = ""
        ) {
            if(it) {
                EditItem(
                    title = title,
                    content = content,
                    onTitleUpdate = onTitleUpdate,
                    onContentUpdate = onContentUpdate,
                    onClickCancel = onClickCancel,
                    onClickSave = onClickSave
                )
            } else {
                BaseItem(
                    item = item,
                    checked = checked,
                    showDelete = showDelete,
                    onClickToDelete = onClickToDelete
                )
            }
        }
    }
}
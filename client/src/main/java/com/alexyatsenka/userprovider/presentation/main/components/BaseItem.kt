package com.alexyatsenka.userprovider.presentation.main.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexyatsenka.models.domain.Note

@Composable
fun BaseItem(
    item: Note,
    checked : Boolean,
    showDelete : Boolean,
    onClickToDelete : () -> Unit,
) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = item.title)
            Text(text = item.content)
        }
        AnimatedVisibility(visible = showDelete) {
            Checkbox(
                checked = checked,
                onCheckedChange = { onClickToDelete() }
            )
        }
    }
}
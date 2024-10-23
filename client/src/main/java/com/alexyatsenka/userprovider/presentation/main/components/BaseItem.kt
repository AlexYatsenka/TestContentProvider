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
import com.alexyatsenka.userprovider.presentation.main.MainViewModel

@Composable
fun BaseItem(item: Note, viewModel : MainViewModel) {
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
        AnimatedVisibility(visible = viewModel.showDelete) {
            Checkbox(
                checked = viewModel.selectedItems.contains(item),
                onCheckedChange = { viewModel.clickToDelete(item) }
            )
        }
    }
}
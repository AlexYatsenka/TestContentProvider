package com.alexyatsenka.userprovider.presentation.main.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexyatsenka.models.domain.Note
import com.alexyatsenka.userprovider.presentation.main.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Item(
    item : Note,
    viewModel: MainViewModel,
    modifier : Modifier = Modifier
) {
    Card(
        modifier = modifier
            .animateContentSize()
            .combinedClickable(
                onClick = {
                    if (viewModel.showDelete) {
                        viewModel.clickToDelete(item)
                    }
                },
                onLongClick = {
                    viewModel.showDelete()
                }
            )
    ) {
        Crossfade(
            targetState = viewModel.editedItem == item,
            label = ""
        ) {
            if(it) {
                EditItem(
                    viewModel = viewModel
                )
            } else {
                BaseItem(
                    item = item,
                    viewModel = viewModel
                )
            }
        }
    }
}
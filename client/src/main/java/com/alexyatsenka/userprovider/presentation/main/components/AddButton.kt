package com.alexyatsenka.userprovider.presentation.main.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexyatsenka.userprovider.presentation.main.MainViewModel

@Composable
fun AddButton(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .animateContentSize()
            .clickable { viewModel.clickToAddCard() }
    ) {
        Crossfade(
            targetState = viewModel.expand,
            label = ""
        ) {
            if(it) {
                ExpandAddButton(viewModel)
            } else {
                CollapsedAddButton()
            }
        }
    }
}
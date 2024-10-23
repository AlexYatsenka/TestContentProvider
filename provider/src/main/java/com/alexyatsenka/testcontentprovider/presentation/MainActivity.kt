package com.alexyatsenka.testcontentprovider.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexyatsenka.models.domain.Note
import com.alexyatsenka.testcontentprovider.di.Dagger
import com.alexyatsenka.testcontentprovider.presentation.theme.TestContentProviderTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Dagger.buildAppComponent(this).inject(this)
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Inject
    fun inject(viewModel : MainViewModel) {
        setContent {
            val items by viewModel.notes.collectAsState(emptyList())
            TestContentProviderTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        AnimatedVisibility(viewModel.showDelete) {
                            FloatingActionButton(onClick = { viewModel.deleteNotes() }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(16.dp)
                        ) {
                            item {
                                Text(
                                    text = "Provider",
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                            items(items) {
                                Item(
                                    item = it,
                                    viewModel = viewModel,
                                    modifier = Modifier.animateItemPlacement()
                                )
                            }
                            item {
                                AddButton(
                                    viewModel = viewModel,
                                    modifier = Modifier.animateItemPlacement()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun Item(
        item : Note,
        viewModel: MainViewModel,
        modifier : Modifier = Modifier
    ) {
        Card(
            modifier = modifier.combinedClickable(
                onClick = {
                    if(viewModel.showDelete) {
                        viewModel.clickToDelete(item)
                    }
                },
                onLongClick = {
                    viewModel.showDelete()
                }
            )
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
                AnimatedVisibility(visible = viewModel.showDelete) {
                    Checkbox(
                        checked = viewModel.listToDelete.contains(item),
                        onCheckedChange = { viewModel.clickToDelete(item) }
                    )
                }
            }
        }
    }

    @Composable
    private fun AddButton(
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

    @Composable
    private fun ExpandAddButton(viewModel: MainViewModel) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = viewModel.currentTitle,
                onValueChange = { viewModel.currentTitle = it },
                label = { Text(text = "Text") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = viewModel.currentContent,
                onValueChange = { viewModel.currentContent = it },
                label = { Text(text = "Content") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { viewModel.addNewNote() }
            ) { Text(text = "Add new note") }
        }
    }

    @Composable
    private fun CollapsedAddButton() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
            Spacer(
                modifier = Modifier.width(8.dp)
            )
            Text(text = "Add new note")
        }
    }
}
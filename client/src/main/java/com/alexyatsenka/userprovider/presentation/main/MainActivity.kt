package com.alexyatsenka.userprovider.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexyatsenka.userprovider.R
import com.alexyatsenka.userprovider.di.Dagger
import com.alexyatsenka.userprovider.presentation.main.components.AddButton
import com.alexyatsenka.userprovider.presentation.main.components.Item
import com.alexyatsenka.userprovider.presentation.theme.TestContentProviderTheme
import kotlinx.coroutines.launch
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
            var currentTitle by remember { mutableStateOf("") }
            var currentContent by remember { mutableStateOf("") }

            val items by viewModel.notes.collectAsState(emptyList())
            val showDelete by viewModel.showDelete.collectAsState()
            val selectedItems by viewModel.selectedItems.collectAsState()
            val expand by viewModel.expand.collectAsState()
            val editedItem by viewModel.editedItem.collectAsState()

            LaunchedEffect(Unit) {
                launch {
                    viewModel.currentTitle.collect { currentTitle = it }
                }
                launch {
                    viewModel.currentContent.collect { currentContent = it }
                }
            }

            TestContentProviderTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AnimatedVisibility(
                                showDelete && selectedItems.size == 1
                            ) {
                                FloatingActionButton(onClick = viewModel::clickToEdit) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = null
                                    )
                                }
                            }
                            AnimatedVisibility(
                                showDelete && selectedItems.isNotEmpty()
                            ) {
                                FloatingActionButton(onClick = { viewModel.deleteNotes() }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(16.dp)
                            ) {
                                item {
                                    Text(
                                        text = stringResource(R.string.main_title),
                                        fontSize = 18.sp,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                                items(items) {
                                    Item(
                                        item = it,
                                        showEdit = editedItem == it,
                                        showDelete = showDelete,
                                        checked = selectedItems.contains(it),
                                        title = currentTitle,
                                        content = currentContent,
                                        onTitleUpdate = {
                                            currentTitle = it
                                            viewModel.setTitle(it)
                                        },
                                        onContentUpdate = {
                                            currentContent = it
                                            viewModel.setContent(it)
                                        },
                                        onClickCancel = viewModel::cancelEdit,
                                        onClickSave = viewModel::saveEditItem,
                                        onClickToDelete = { viewModel.clickToDelete(it) },
                                        onShowDelete = viewModel::showDelete,
                                        modifier = Modifier.animateItemPlacement()
                                    )
                                }
                                item {
                                    AddButton(
                                        title = currentTitle,
                                        content = currentContent,
                                        onTitleUpdate = {
                                            currentTitle = it
                                            viewModel.setTitle(it)
                                        },
                                        onContentUpdate = {
                                            currentContent = it
                                            viewModel.setContent(it)
                                        },
                                        onAddNote = viewModel::addNewNote,
                                        onClick = viewModel::clickToAddCard,
                                        expand = expand,
                                        modifier = Modifier.animateItemPlacement()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
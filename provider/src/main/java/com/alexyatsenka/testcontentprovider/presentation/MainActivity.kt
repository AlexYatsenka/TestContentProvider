package com.alexyatsenka.testcontentprovider.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
                                    modifier = Modifier.animateItemPlacement()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun Item(
        item : Note,
        modifier : Modifier = Modifier
    ) {
        Card(modifier = modifier) {
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
            }
        }
    }
}
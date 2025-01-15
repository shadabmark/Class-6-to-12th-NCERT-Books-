package com.example.ncertclass6to12books.ui_layer.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ncertclass6to12books.ui.theme.AppColor
import com.example.ncertclass6to12books.ui_layer.AppNavigation.Routes
import com.example.ncertclass6to12books.ui_layer.AppViewModel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAllChaptersUI(
    navController: NavController,
    cId: String,
    viewModel: AppViewModel = hiltViewModel(),
) {
    val state = viewModel.getAllBooksState.collectAsState()
    val data = state.value.data
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllBookState(cId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ALL CHAPTERS",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(26.dp)
                            .clickable {
                                navController.navigateUp()
                            }
                    )
                },
                modifier = Modifier.height(84.dp),
                colors = TopAppBarDefaults.topAppBarColors(AppColor)
            )
        }
    ) { paddingValue ->
        when {
            state.value.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.value.error.isNotEmpty() -> {
                Toast.makeText(context, state.value.error, Toast.LENGTH_SHORT).show()
            }

            state.value.data.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValue)
                ) {
                    items(data) { chapter ->
                        chapter?.let {
                            GetAllChapters(
                                bookName = it.chapterName,
                                onItemClick = {
                                    navController.navigate(Routes.PDFViewer(it.chapterUrl))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GetAllChapters(
    bookName: String,
    onItemClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .size(height = 110.dp, width = 400.dp)
            .padding(start = 12.dp, end = 0.dp, top = 12.dp, bottom = 8.dp)
            .clickable { onItemClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = bookName, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
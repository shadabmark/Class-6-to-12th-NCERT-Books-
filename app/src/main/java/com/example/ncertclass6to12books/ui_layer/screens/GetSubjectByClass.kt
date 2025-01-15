package com.example.ncertclass6to12books.ui_layer.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ncertclass6to12books.ui.theme.AppColor
import com.example.ncertclass6to12books.ui_layer.AppNavigation.Routes
import com.example.ncertclass6to12books.ui_layer.AppViewModel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetSubjectByClassUI(
    navController: NavController,
    subjectId: String,
    viewModel: AppViewModel = hiltViewModel()
) {
    val state = viewModel.getSubjectByClassState.collectAsState()
    val data = state.value.data
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.getSubjectByClass(subjectId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "PART OF BOOK",
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
                            .clickable { navController.navigateUp() }
                    )
                },
                modifier = Modifier.height(84.dp),
                colors = TopAppBarDefaults.topAppBarColors(AppColor)
            )
        }
    ) { paddingValues ->
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
                        .padding(paddingValues)
                ) {
                    items(data) { item ->
                        item?.let {
                            GetSubjectByClass(
                                bookName = it.BookName,
                                bookImageUrl = it.bookImageUrl,
                                navController = navController,
                                cId = it.chapterId
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GetSubjectByClass(
    bookName: String,
    bookImageUrl: String,
    cId: String,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .height(170.dp)
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { navController.navigate(Routes.AllChapters(cId = cId)) }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = bookImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(132.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = bookName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}